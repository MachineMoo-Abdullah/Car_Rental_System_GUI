import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.sound.midi.ShortMessage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Rental extends JFrame implements ActionListener{
    JFrame jf;
    JLabel l1, l2, l3, l4, l5, l6;
    JTextField t2, t3, t4;
    JDateChooser rentalDateChooser, dueDateChooser;
    JComboBox<String> carIdComboBox;
    JButton back_btn, b1, b2, b3;
    ConnectionClass con;
    PreparedStatement pst,pst1;

    Rental() {
        jf = new JFrame("Rental Information");
        jf.setBackground(Color.WHITE);
        jf.setSize(500, 500); // Adjusted size
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.setLayout(null);

        // Labels and TextFields
        l1 = new JLabel("Car ID");
        l1.setBounds(50, 50, 200, 30);
        l1.setFont(new Font("Arial", Font.BOLD, 15));
        jf.add(l1);

        l2 = new JLabel("Customer ID");
        l2.setBounds(50, 100, 150, 30);
        l2.setFont(new Font("Arial", Font.BOLD, 15));
        jf.add(l2);

        l3 = new JLabel("Customer Name");
        l3.setBounds(50, 150, 150, 30);
        l3.setFont(new Font("Arial", Font.BOLD, 15));
        jf.add(l3);

        l4 = new JLabel("Rental Fee");
        l4.setFont(new Font("Arial", Font.BOLD, 15));
        l4.setBounds(50, 200, 100, 30);
        jf.add(l4);

        l5 = new JLabel("Rental Date");
        l5.setFont(new Font("Arial", Font.BOLD, 15));
        l5.setBounds(50, 250, 100, 30);
        jf.add(l5);

        l6 = new JLabel("Due Date");
        l6.setFont(new Font("Arial", Font.BOLD, 15));
        l6.setBounds(50, 300, 100, 30);
        jf.add(l6);

        carIdComboBox = new JComboBox<>();
        carIdComboBox.setBounds(250, 50, 150, 30);
        carIdComboBox.addItemListener(new ItemListener() {
            private boolean userSelected = false; // Flag to track user selection

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED ) {
                    if (userSelected) {
                        enableTextFields(true);
                    }
                    userSelected = true; // Set the flag to true after user selection
                } else {
                    enableTextFields(false);
                }
            }
        });
        carIdComboBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    t2.requestFocusInWindow(); // Move focus to t2
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        jf.add(carIdComboBox);

        t2 = new JTextField();
        t2.setBounds(250, 100, 150, 30);
        t2.setEditable(false); // Initially non-editable
        t2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String customerId = t2.getText().trim();
                if (!customerId.isEmpty()) {
                    fetchCustomerName(customerId);
                }
            }
        });
        t2.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    t3.requestFocusInWindow(); // Move focus to t2
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        jf.add(t2);


        t3 = new JTextField();
        t3.setBounds(250, 150, 150, 30);
        t3.setEditable(false); // Initially non-editable
        t3.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    t4.requestFocusInWindow(); // Move focus to t2
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        jf.add(t3);

        t4 = new JTextField();
        t4.setBounds(250, 200, 150, 30);
        t4.setEditable(false); // Initially non-editable
        jf.add(t4);

        rentalDateChooser = new JDateChooser();
        rentalDateChooser.setBounds(250, 250, 150, 30);
        rentalDateChooser.setEnabled(false); // Initially disabled
        JTextFieldDateEditor rentalEditor = (JTextFieldDateEditor) rentalDateChooser.getDateEditor();
        rentalEditor.setEditable(false); // Make non-editable
        rentalEditor.setDateFormatString("yyyy-MM-dd");
        jf.add(rentalDateChooser);

        // Due Date Chooser
        dueDateChooser = new JDateChooser();
        dueDateChooser.setBounds(250, 300, 150, 30);
        dueDateChooser.setEnabled(false); // Initially disabled
        JTextFieldDateEditor dueEditor = (JTextFieldDateEditor) dueDateChooser.getDateEditor();
        dueEditor.setEditable(false); // Make non-editable
        dueEditor.setDateFormatString("yyyy-MM-dd");
        jf.add(dueDateChooser);


        b1 = new JButton("OK");
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setBounds(100, 350, 120, 30);
        b1.addActionListener(this);
        jf.add(b1);

        b2 = new JButton("Cancel");
        b2.setBackground(Color.RED);
        b2.setForeground(Color.WHITE);
        b2.setBounds(250, 350, 120, 30);
        b2.addActionListener(this);
        jf.add(b2);

        jf.setVisible(true);

        // Fetch available car IDs
        fetchCarIds();
    }
    // Define a method to add rental details to the database
    private void addRentalDetails() {
        String carId = ((String) carIdComboBox.getSelectedItem());
        String customerId = t2.getText();

        String customerName = t3.getText().trim();
        String rentalFee = t4.getText().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String rentalDate = sdf.format(rentalDateChooser.getDate());
        String dueDate = sdf.format(dueDateChooser.getDate());

        if (customerId.isEmpty() || customerName.isEmpty() || rentalFee.isEmpty() ||rentalDate.isEmpty() || dueDate.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Check if the car is already booked
            pst1 = con.com.prepareStatement("SELECT * FROM rentals WHERE car_id = ?");
            pst1.setString(1, carId);
            ResultSet rs = pst1.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(jf, "Car already booked!", "Booking Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // If the car is not already booked, proceed with the insertion
                pst = con.com.prepareStatement("INSERT INTO rentals (car_id, customer_id, customer_name, rental_fee,rental_date,due_date) VALUES (?, ?, ?,?,?,?)");
                pst.setString(1, carId);
                pst.setString(2, customerId);
                pst.setString(3, customerName);
                pst.setString(4, rentalFee);
                pst.setString(5, rentalDate);
                pst.setString(6, dueDate);
                pst.executeUpdate();

                pst1 = con.com.prepareStatement("UPDATE cars SET available = 0 WHERE carRegno = ?");
                pst1.setString(1, carId);
                pst1.executeUpdate();
                JOptionPane.showMessageDialog(jf, "Rental details added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Clear fields after successful insertion
                t2.setText("");
                t3.setText("");
                t4.setText("");
                rentalDateChooser.setDate(null);
                dueDateChooser.setDate(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(jf, "Failed to add rental details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fetchCustomerName(String customerId) {
        try {
            con = new ConnectionClass();
            PreparedStatement pst = con.com.prepareStatement("SELECT customerName FROM customers WHERE customerId = ?");
            pst.setString(1, customerId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String customerName = rs.getString("customerName");
                t3.setText(customerName);
            } else {
                t3.setText(""); // Clear the customer name field if no matching ID found
                JOptionPane.showMessageDialog(jf, "Customer ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchCarIds() {
        try {
            carIdComboBox.addItem("");
            con = new ConnectionClass();
            PreparedStatement pst = con.com.prepareStatement("SELECT carRegno FROM cars WHERE available = true");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                carIdComboBox.addItem(rs.getString("carRegno"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void enableTextFields(boolean enabled) {
        t2.setEditable(enabled);
        t3.setEditable(enabled);
        t4.setEditable(enabled);
        rentalDateChooser.setEnabled(enabled);
        dueDateChooser.setEnabled(enabled);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
                addRentalDetails();
            }
        if(e.getSource() == b2)
        {
            new Home();
        }
    }

}
