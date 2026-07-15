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

public class Customer extends JFrame implements ActionListener {
    JFrame jf;
    ConnectionClass c;
    PreparedStatement pst;
    JLabel l1, l2, l3, l4;
    JTextField t1, t2, t3, t4;
    JTextArea addressArea;
    JButton back_btn, b1, b2, b3;
    JTable table;

    Customer() {
        jf = new JFrame("Register a Customer");
        jf.setBackground(Color.WHITE);
        jf.setSize(1200, 500); // Adjusted size
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.setLayout(null);

        // Labels and TextFields
        l1 = new JLabel("Customer ID");
        l1.setBounds(50, 50, 200, 30);
        l1.setFont(new Font("Arial", Font.BOLD, 15));
        jf.add(l1);

        l2 = new JLabel("Customer Name");
        l2.setBounds(50, 100, 150, 30);
        l2.setFont(new Font("Arial", Font.BOLD, 15));
        jf.add(l2);

        l3 = new JLabel("Address");
        l3.setBounds(50, 150, 100, 30);
        l3.setFont(new Font("Arial", Font.BOLD, 15));
        jf.add(l3);

        l4 = new JLabel("Mobile");
        l4.setFont(new Font("Arial", Font.BOLD, 15));
        l4.setBounds(50, 250, 100, 30);
        jf.add(l4);

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
        t3.setBounds(250, 250, 150, 30);
        jf.add(t3);

        addressArea = new JTextArea();
        addressArea.setBounds(250, 150, 150, 100);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(addressArea);
        scrollPane.setBounds(250, 150, 150, 70);
        jf.add(scrollPane);

        b1 = new JButton("Add");
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
        model.addColumn("Customer ID");
        model.addColumn("Customer Name");
        model.addColumn("Address");
        model.addColumn("Mobile");

        table = new JTable(model);
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(table));

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(450, 50, 700, 300);
        jf.add(tableScrollPane);

        jf.setVisible(true);

        c = new ConnectionClass();
        Show_table();
    }

    public void Show_table() {
        try {
            pst = c.com.prepareStatement("SELECT * FROM customers");
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing rows
            while (rs.next()) {
                String customerID = rs.getString("customerID");
                String customerName = rs.getString("customerName");
                String address = rs.getString("address");
                String mobile = rs.getString("mobile");
                model.addRow(new Object[]{customerID, customerName, address, mobile});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCustomer() {
        String customerID = t1.getText();
        String customerName = t2.getText();
        String address = addressArea.getText();
        String mobile = t3.getText();
        if (customerID.isEmpty() || customerName.isEmpty() || address.isEmpty() || mobile.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            pst = c.com.prepareStatement("SELECT * FROM customers WHERE customerID = ?");
            pst.setString(1, customerID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(jf, "Customer ID already exists!", "Registration Error", JOptionPane.ERROR_MESSAGE);
            } else {
                pst = c.com.prepareStatement("INSERT INTO customers (customerID, customerName, address, mobile) VALUES (?, ?, ?, ?)");
                pst.setString(1, customerID);
                pst.setString(2, customerName);
                pst.setString(3, address);
                pst.setString(4, mobile);
                pst.executeUpdate();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{customerID, customerName, address, mobile});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            addCustomer();
        }
        else if (e.getSource() == back_btn) {
            this.jf.setVisible(false);
            new Home(); // Assuming Home is defined elsewhere
        }
    }
}

class HeaderRenderer implements TableCellRenderer {
    private final TableCellRenderer delegate;

    public HeaderRenderer(JTable table) {
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
