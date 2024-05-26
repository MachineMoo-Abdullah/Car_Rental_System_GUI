import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame implements ActionListener {
    JButton b1, b2, b3, b4, b5,back_btn;
    JFrame jf;
    JLabel label,heading;
    JPanel panel = new JPanel();

    Home() {
        panel.setBackground(Color.RED);
        panel.setLayout(null);
        panel.setBounds(0, 0, 200, 1000);

        jf = new JFrame("Home Page");
        jf.setBackground(Color.WHITE);
        jf.setSize(1000, 1000);
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.setLayout(null);
        label = new JLabel(); // Instantiate the JLabel object
        heading = new JLabel("Car Rental System");

        heading.setFont(new Font("Arial", Font.BOLD, 30)); // Setting font and size
        heading.setForeground(Color.gray);
        heading.setBounds(400, 30, 300, 30); // Setting bounds
        jf.add(heading); // Adding heading to the JFrame

        b1 = new JButton("Car Registration");
        b1.setForeground(Color.BLACK);
        b1.setBounds(14, 100, 150, 30);
        b1.addActionListener(this);

        b2 = new JButton("Customer");
        b2.setForeground(Color.BLACK);
        b2.setBounds(14, 150, 150, 30);
        b2.addActionListener(this);

        b3 = new JButton("Rental");
        b3.setForeground(Color.BLACK);
        b3.setBounds(14, 200, 150, 30);
        b3.addActionListener(this);

        b4 = new JButton("Return");
        b4.setForeground(Color.BLACK);
        b4.setBounds(14, 250, 150, 30);
        b4.addActionListener(this);

        b5 = new JButton("Log out");
        b5.setForeground(Color.BLACK);
        b5.setBounds(14, 300, 150, 30);
        b5.addActionListener(this);

        label.setBounds(300, 200, 580, 350);
        try {
            String filePath = "C://Users//Ayesha fakhar//Desktop//Program_uni//car_rental_system//Icons//cars.png";
            ImageIcon icon = new ImageIcon(filePath);

            if (icon.getImageLoadStatus() == MediaTracker.ERRORED) {
                System.out.println("Image could not be loaded");
            } else {
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(580, 350, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImg);
                label.setIcon(scaledIcon);  // Set the scaled icon to the label
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);
        panel.add(b5);
        jf.add(panel);
        jf.add(label);
        jf.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            this.jf.setVisible(false);
            new Car_register();
        }
        else if(e.getSource() == b2)
        {
            this.jf.setVisible(false);
            new Customer();
        } else if (e.getSource() == b3) {
            this.jf.setVisible(false);
            new Rental();
        } else if (e.getSource() == b4) {
            this.jf.setVisible(false);
            new Return();
        } else if(e.getSource() == b5)
        {
            JOptionPane.showMessageDialog(jf, "Logged Out Successfully" );
            this.jf.setVisible(false);

            new Login();
        }

    }


}

