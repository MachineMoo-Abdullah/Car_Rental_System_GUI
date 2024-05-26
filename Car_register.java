import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;

public class Car_register extends JFrame implements ActionListener {
    JFrame jf;
    ConnectionClass c;
    PreparedStatement pst;
    JLabel l1, l2, l3, l4, l5;
    JTextField t1, t2, t3, t4;
    JComboBox<String> availabilityComboBox;
    JButton back_btn, b1, b2, b3;
    JTable table;

    Car_register() {
        jf = new JFrame("Register a Car");
        jf.setBackground(Color.WHITE);
        jf.setSize(1200, 500); // Adjusted size
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.setLayout(null);

        // Labels and TextFields
        l1 = new JLabel("Car Registration Number");
        l1.setBounds(50, 50, 200, 30);
        l1.setFont(new Font("Arial", Font.BOLD, 15));
        jf.add(l1);

        l2 = new JLabel("Make");
        l2.setBounds(50, 100, 100, 30);
        l2.setFont(new Font("Arial", Font.BOLD, 15));
        jf.add(l2);

        l3 = new JLabel("Model");
        l3.setBounds(50, 150, 100, 30);
        l3.setFont(new Font("Arial", Font.BOLD, 15));
        jf.add(l3);

        l4 = new JLabel("Car Name"); // Changed label text
        l4.setFont(new Font("Arial", Font.BOLD, 15));
        l4.setBounds(50, 200, 100, 30); // Adjusted position
        jf.add(l4);

        l5 = new JLabel("Available");
        l5.setFont(new Font("Arial", Font.BOLD, 15));
        l5.setBounds(50, 250, 100, 30);
        jf.add(l5);

        t1 = new JTextField();
        t1.setBounds(250, 50, 150, 30);
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

        t4 = new JTextField(); // Added JTextField for "Car Name"
        t4.setBounds(250, 200, 150, 30);
        jf.add(t4);

        String[] availabilityOptions = {"Yes", "No"};
        availabilityComboBox = new JComboBox<>(availabilityOptions);
        availabilityComboBox.setBounds(250, 250, 150, 30); // Adjusted position
        jf.add(availabilityComboBox);

        b1 = new JButton("Add");
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setBounds(100, 300, 120, 30); // Adjusted position
        b1.addActionListener(this);
        jf.add(b1);

        b2 = new JButton("Edit");
        b2.setBackground(Color.RED);
        b2.setForeground(Color.WHITE);
        b2.setBounds(250, 300, 120, 30); // Adjusted position
        b2.addActionListener(this);
        jf.add(b2);

        b3 = new JButton("Delete");
        b3.setBackground(Color.RED);
        b3.setForeground(Color.WHITE);
        b3.setBounds(100, 350, 120, 30); // Adjusted position
        b3.addActionListener(this);
        jf.add(b3);

        back_btn = new JButton("Back");
        back_btn.setBackground(Color.BLACK);
        back_btn.setForeground(Color.WHITE);
        back_btn.setFont(new Font("Arial", Font.BOLD, 15));
        back_btn.setBounds(250, 350, 120, 30); // Adjusted position
        back_btn.addActionListener(this);
        jf.add(back_btn);

        // Create a table model
        DefaultTableModel model = new DefaultTableModel();

        // Add columns to the model
        model.addColumn("Car Registration");
        model.addColumn("Make");
        model.addColumn("Model");
        model.addColumn("Car Name"); // Added column
        model.addColumn("Available");

        // Create a JTable with the model
        table = new JTable(model);

        // Customize the table header
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer1(table));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(450, 50, 700, 300); // Adjusted bounds
        jf.add(scrollPane);

        jf.setVisible(true);

        try {
            c = new ConnectionClass();  // Assuming ConnectionClass is defined elsewhere
            pst = c.com.prepareStatement("select * from cars");
            Show_table();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back_btn) {
            this.jf.setVisible(false);
            new Home();  // Assuming Home is defined elsewhere
        } else if (e.getSource() == b1) {
            // Add button action
            addCar();
        } else if (e.getSource() == b2) {
            editCar();
        } else if(e.getSource() == b3) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String carRegno = table.getValueAt(selectedRow, 0).toString(); // Get car registration number from the selected row
                try {
                    pst = c.com.prepareStatement("DELETE FROM cars WHERE carRegno = ?");
                    pst.setString(1, carRegno);
                    pst.executeUpdate();

                    // Remove the row from the JTable
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.removeRow(selectedRow);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(jf, "Please select a row to delete.", "Delete Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == back_btn || e.getSource() == b1 || e.getSource() == b2 || e.getSource() == b3) {
            // If any of the buttons are clicked, clear the text fields
            t1.setText(""); // Clear car registration number field
            t2.setText(""); // Clear make field
            t3.setText(""); // Clear model field
            t4.setText(""); // Clear car name field
        }
    }

    public void Show_table() {
        try {
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            while (rs.next()) {
                String carRegno = rs.getString("carRegno");
                String make = rs.getString("make");
                String modelStr = rs.getString("model");
                String carName = rs.getString("carName"); // Added field
                boolean available = rs.getBoolean("available");
                model.addRow(new Object[]{carRegno, make, modelStr, carName, available ? "Yes" : "No"});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void editCar() {
        String carRegno = JOptionPane.showInputDialog(jf, "Enter Car Registration Number to Edit:");
        if (carRegno != null && !carRegno.isEmpty()) {
            try {
                pst = c.com.prepareStatement("SELECT * FROM cars WHERE carRegno = ?");
                pst.setString(1, carRegno);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    // Car registration number found, display current details for editing
                    String make = rs.getString("make");
                    String modelStr = rs.getString("model");
                    String carName = rs.getString("carName");
                    boolean available = rs.getBoolean("available");

                    // Prompt user to edit the details
                    String newMake = JOptionPane.showInputDialog(jf, "Enter New Make (Current: " + make + "):", make);
                    String newModel = JOptionPane.showInputDialog(jf, "Enter New Model (Current: " + modelStr + "):", modelStr);
                    String newCarName = JOptionPane.showInputDialog(jf, "Enter New Car Name (Current: " + carName + "):", carName);
                    String newAvailable = (String) JOptionPane.showInputDialog(jf, "Is Car Available? (Current: " + (available ? "Yes" : "No") + "):", available ? "Yes" : "No", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, "Yes");

                    if (newMake != null && newModel != null && newCarName != null && newAvailable != null) {
                        // Update the data in the database
                        pst = c.com.prepareStatement("UPDATE cars SET make=?, model=?, carName=?, available=? WHERE carRegno=?");
                        pst.setString(1, newMake);
                        pst.setString(2, newModel);
                        pst.setString(3, newCarName);
                        pst.setBoolean(4, newAvailable.equals("Yes"));
                        pst.setString(5, carRegno);
                        pst.executeUpdate();

                        // Update the data in the JTable
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        for (int i = 0; i < model.getRowCount(); i++) {
                            if (model.getValueAt(i, 0).equals(carRegno)) {
                                model.setValueAt(newMake, i, 1);
                                model.setValueAt(newModel, i, 2);
                                model.setValueAt(newCarName, i, 3);
                                model.setValueAt(newAvailable, i, 4);
                                break;
                            }
                        }

                        // Inform the user that the data has been updated
                        JOptionPane.showMessageDialog(jf, "Car data updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(jf, "Edit canceled by user.", "Edit Canceled", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(jf, "Car registration number not found!", "Edit Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(jf, "Please enter a valid car registration number.", "Edit Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCar() {
        String carRegno = t1.getText();
        String make = t2.getText();
        String modelStr = t3.getText();
        String carName = t4.getText(); // Get "Car Name" value
        String available = (String) availabilityComboBox.getSelectedItem();
        if (carRegno.isEmpty() || make.isEmpty() || modelStr.isEmpty() || carName.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            pst = c.com.prepareStatement("SELECT * FROM cars WHERE carRegno = ?");
            pst.setString(1, carRegno);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // Car registration number already exists
                JOptionPane.showMessageDialog(jf, "Car registration number already exists!", "Registration Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Car registration number doesn't exist, proceed with adding the car
                pst = c.com.prepareStatement("INSERT INTO cars (carRegno, make, model, carName, available) VALUES (?, ?, ?, ?, ?)");
                pst.setString(1, carRegno);
                pst.setString(2, make);
                pst.setString(3, modelStr);
                pst.setString(4, carName); // Set "Car Name" value
                pst.setBoolean(5, available.equals("Yes"));
                pst.executeUpdate();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{carRegno, make, modelStr, carName, available});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
class HeaderRenderer1 implements TableCellRenderer {
    private final TableCellRenderer delegate;

    public HeaderRenderer1(JTable table) {
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
