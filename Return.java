import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Return extends JFrame implements ActionListener {
    JFrame jf;
    ConnectionClass c;
    PreparedStatement pst, pst1, pst2;
    JLabel l1, l2, l3, l4, l5;
    JTextField t1, t2, t3, t4, t5;
    JButton back_btn, b1;
    JTable table;

    Return() {
        jf = new JFrame("Return a Car");
        jf.setBackground(Color.WHITE);
        jf.setSize(1200, 500); // Adjusted size
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

        l3 = new JLabel("Return Date");
        l3.setBounds(50, 150, 150, 30);
        l3.setFont(new Font("Arial", Font.BOLD, 15));
        jf.add(l3);

        l4 = new JLabel("Days Elapsed");
        l4.setFont(new Font("Arial", Font.BOLD, 15));
        l4.setBounds(50, 200, 100, 30);
        jf.add(l4);

        l5 = new JLabel("Fine");
        l5.setFont(new Font("Arial", Font.BOLD, 15));
        l5.setBounds(50, 250, 100, 30);
        jf.add(l5);

        t1 = new JTextField();
        t1.setBounds(250, 50, 150, 30);
        t1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                fetchRentalDetails(t1.getText().trim());
            }
        });
        t1.addKeyListener(new KeyListener() {
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
        jf.add(t1);

        t2 = new JTextField();
        t2.setBounds(250, 100, 150, 30);
        t2.setEditable(false); // Make it non-editable
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
        t3.setEditable(false); // Make it non-editable
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
        t4.setEditable(false); // Make it non-editable
        t4.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    t5.requestFocusInWindow(); // Move focus to t2
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        jf.add(t4);

        t5 = new JTextField();
        t5.setBounds(250, 250, 150, 30);
        t5.setEditable(false); // Make it non-editable
        jf.add(t5);

        b1 = new JButton("Return Car");
        b1.setBackground(Color.RED);
        b1.setForeground(Color.WHITE);
        b1.setBounds(100, 300, 120, 30);
        b1.addActionListener(this);
        jf.add(b1);

        back_btn = new JButton("Back");
        back_btn.setBackground(Color.BLACK);
        back_btn.setForeground(Color.WHITE);
        back_btn.setFont(new Font("Arial", Font.BOLD, 15));
        back_btn.setBounds(250, 300, 120, 30);
        back_btn.addActionListener(this);
        jf.add(back_btn);

        // Create a table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Car ID");
        model.addColumn("Customer ID");
        model.addColumn("Return Date");
        model.addColumn("Days Elapsed");
        model.addColumn("Fine");

        table = new JTable(model);
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer3(table));

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(450, 50, 700, 300);
        jf.add(tableScrollPane);

        jf.setVisible(true);

        c = new ConnectionClass();
        Show_table();
    }

    public void Show_table() {
        try {
            pst = c.com.prepareStatement("SELECT * FROM returns");
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing rows
            while (rs.next()) {
                String carID = rs.getString("car_id");
                String customerID = rs.getString("customer_id");
                String returnDate = rs.getString("return_date");
                int daysElapsed = rs.getInt("days_elapsed");
                double fine = rs.getDouble("fine");
                model.addRow(new Object[]{carID, customerID, returnDate, daysElapsed, fine});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchRentalDetails(String carID) {
        if (carID.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Please enter a Car ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            pst = c.com.prepareStatement("SELECT * FROM rentals WHERE car_id = ?");
            pst.setString(1, carID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String customerID = rs.getString("customer_id");
                Date rentalDate = rs.getDate("rental_date");
                Date dueDate = rs.getDate("due_date");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String returnDate = sdf.format(new Date());

                long daysElapsed = TimeUnit.MILLISECONDS.toDays(new Date().getTime() - rentalDate.getTime());
                long daysOverdue = TimeUnit.MILLISECONDS.toDays(new Date().getTime() - dueDate.getTime());
                double fine = daysOverdue > 0 ? daysOverdue * 50 : 0; // Assuming a fine of 50 per day overdue

                t2.setText(customerID);
                t3.setText(returnDate);
                t4.setText(String.valueOf(daysElapsed));
                t5.setText(String.valueOf(fine));
            } else {
                JOptionPane.showMessageDialog(jf, "No rental record found for the entered Car ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(jf, "Error fetching rental details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnCar() {
        String carID = t1.getText();
        String customerID = t2.getText();
        String returnDate = t3.getText();
        int daysElapsed = Integer.parseInt(t4.getText());
        double fine = Double.parseDouble(t5.getText());

        if (carID.isEmpty() || customerID.isEmpty() || returnDate.isEmpty() || t4.getText().isEmpty() || t5.getText().isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            pst = c.com.prepareStatement("INSERT INTO returns (car_id, customer_id, return_date, days_elapsed, fine) VALUES (?, ?, ?, ?, ?)");
            pst.setString(1, carID);
            pst.setString(2, customerID);
            pst.setString(3, returnDate);
            pst.setInt(4, daysElapsed);
            pst.setDouble(5, fine);
            pst.executeUpdate();

            pst1 = c.com.prepareStatement("UPDATE cars SET available = 1 WHERE carRegno = ?");

            pst1.setString(1, carID);
            pst1.executeUpdate();

            pst2 = c.com.prepareStatement("DELETE FROM rentals WHERE car_id = ?");
            pst2.setString(1, carID);
            pst2.executeUpdate();

            JOptionPane.showMessageDialog(jf, "Car return details added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(new Object[]{carID, customerID, returnDate, daysElapsed, fine});

            // Clear fields after successful insertion
            t1.setText("");
            t2.setText("");
            t3.setText("");
            t4.setText("");
            t5.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(jf, "Failed to add return details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            returnCar();
        } else if (e.getSource() == back_btn) {
            this.jf.setVisible(false);
            new Home(); // Assuming Home is defined elsewhere
        }
    }
}

class HeaderRenderer3 implements TableCellRenderer {
    private final TableCellRenderer delegate;

    public HeaderRenderer3(JTable table) {
        delegate = table.getTableHeader().getDefaultRenderer();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        JLabel label = (JLabel) component;
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD)); // Make header text bold
        return component;
    }
}
