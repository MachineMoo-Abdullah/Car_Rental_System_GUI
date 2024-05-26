import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;
public class SignUp_page extends JFrame implements ActionListener {
    JFrame jf;
    JLabel l1,l2,l3,l4,l5;
    JTextField t1,t2,t3;
    JPasswordField p1;
    JButton b1,b2;
    SignUp_page()
    {
        jf= new JFrame("Create New Account");
        jf.setBackground(Color.WHITE);
        jf.setSize(580, 350);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.setLayout(null);

        l1 = new JLabel("UserName");
        l1.setBounds(40,20,100,30);
        jf.add(l1);

        l2 = new JLabel("Name");
        l2.setBounds(40,70,100,30);
        jf.add(l2);

        l3 = new JLabel("Password");
        l3.setBounds(40,120,100,30);
        jf.add(l3);

        l4 = new JLabel("Phone no");
        l4.setBounds(40,170,100,30);
        jf.add(l4);

        t1 = new JTextField();
        t1.setBounds(150,20,150,30);
        jf.add(t1);

        t2 = new JTextField();
        t2.setBounds(150,70,150,30);
        jf.add(t2);

        t3 = new JTextField();
        t3.setBounds(150,170,150,30);
        jf.add(t3);

        p1 = new JPasswordField();
        p1.setBounds(150,120,150,30);
        jf.add(p1);

        String filePath = "C://Users//Ayesha fakhar//Desktop//Program_uni//car_rental_system//Icons//signup.png";
        ImageIcon icon = new ImageIcon(filePath);

        if (icon.getImageLoadStatus() == MediaTracker.ERRORED) {
            System.out.println("Image could not be loaded");
        } else {
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImg);
            JLabel l5 = new JLabel(scaledIcon);
            l5.setBounds(350, 70, 150, 150);
            jf.add(l5);
        }
        b1 = new JButton("Sign Up");
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setBounds(40,240,120,30);

        b2 = new JButton("Next");
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        b2.setBounds(200,240,120,30);

        b1.addActionListener(this);
        b2.addActionListener(this);

        jf.add(b1);
        jf.add(b2);

        jf.setSize(580, 350);
        jf.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b1)
        {
            String username = t1.getText();
            String name = t2.getText();
            String password = p1.getText();
            String phone = t3.getText();
            ConnectionClass c = new ConnectionClass();
            String q  = "insert into signup values('"+username+"','"+name+"','"+password+"','"+phone+"')";
            int aa = 0;
            try {
                aa = c.stm.executeUpdate(q);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            if(aa == 1)
            {
                JOptionPane.showMessageDialog(null,"Account Created Successfully");
                this.setVisible(false);
                //new Login();
            }
            else {
                JOptionPane.showMessageDialog(null,"Please!,Fill all the details Carefully");
                this.jf.setVisible(false);
                this.jf.setVisible(true);
            }
        }
        if(e.getSource() == b2)
        {
            this.jf.setVisible(false);
            new Login();
        }
    }
}
