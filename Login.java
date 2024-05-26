import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener {

    JLabel label;
    JFrame jf;
    JLabel l1, l2, l3, l4, l5;
    JTextField t1;
    JPasswordField p1;
    JButton b1, b2;

    Login() {
        jf = new JFrame("Login Account");
        jf.setBackground(Color.WHITE);
        jf.setLayout(null);
        jf.setSize(580, 350);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        // Create a label to hold the background image
        label = new JLabel();
        label.setBounds(0, 0, 580, 350);
        jf.add(label);

        // Load and scale the background image
        String filePath = "C://Users//Ayesha fakhar//Desktop//Program_uni//car_rental_system//Icons//Login_back.jpeg";
        ImageIcon icon = new ImageIcon(filePath);

        if (icon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.out.println("Image could not be loaded");
        } else {
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(580, 350, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImg);
            label.setIcon(scaledIcon);  // Set the scaled icon to the label
        }

        // Add the components to the frame (example components, adjust as needed)
        l2 = new JLabel("Login Account");
        l2.setBounds(190, 30, 500, 50);
        l2.setFont(new Font("Arial",Font.BOLD,30));
        l2.setForeground(Color.RED);
        label.add(l2);


        l3 = new JLabel("Username");
        l3.setBounds(120,120,150,20);
        l3.setForeground(Color.ORANGE);
        l3.setFont(new Font("Arial",Font.BOLD,20));
        label.add(l3);

        l4 = new JLabel("Password");
        l4.setBounds(120,170,150,30);
        l4.setForeground(Color.ORANGE);
        l4.setFont(new Font("Arial",Font.BOLD,20));
        label.add(l4);

        t1 = new JTextField();
        t1.setBounds(300,120,150,25);
        t1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    p1.requestFocusInWindow(); // Move focus to t2
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        jf.add(t1);

        p1 = new JPasswordField();
        p1.setBounds(300,170,150,25);
        jf.add(p1);

        b1 = new JButton("Sign In");
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setBounds(140,250,120,30);

        b2 = new JButton("Sign Up");
        b2.setBackground(Color.RED);
        b2.setForeground(Color.WHITE);
        b2.setBounds(290,250,120,30);

        jf.add(b1);
        jf.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        jf.add(label);
        jf.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            String username = t1.getText();
            String password = p1.getText();
            ConnectionClass c = new ConnectionClass();
            String q = "select * from signup where username ='" + username + "'and password='" + password + "'";
            try {
                ResultSet rs = c.stm.executeQuery(q);
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Login Successfully");
                    jf.setVisible(false);
                    new Home();
                } else {
                    JOptionPane.showMessageDialog(null, "Looks Like You Entered Wrong Username or Password");
                    jf.setVisible(false);
                    jf.setVisible(true);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == b2) {
            jf.setVisible(false);
            new SignUp_page();
        }
    }
    public static void main(String[] args) {
        new Login();
    }
}
