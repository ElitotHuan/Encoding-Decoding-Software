package Project;

import Project.UI.ASymmetricUI.ASymmetricUI;
import Project.UI.HashUI.HashUI;
import Project.UI.SymmetricUI.SymmetricUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

public class Welcome extends JFrame implements ActionListener {

    JPanel p1, p2;
    JLabel l1;
    JButton button1, button2 , button3 ;

    public Welcome() {
        setLayout(new GridLayout(2, 1));
        p1 = new JPanel();
        p2 = new JPanel();

        add(p1);
        add(p2);

        l1 = new JLabel("Welcome");
        l1.setText("Welcome");
        l1.setHorizontalAlignment(JLabel.CENTER);

        p1.add(l1);

        button1 = new JButton("Symmetric");
        button1.addActionListener(this);
        button1.setBounds(130, 50, 100, 40);
        p2.add(button1);

        button2 = new JButton("ASymmetric");
        button2.addActionListener(this);
        button2.setBounds(130, 50, 100, 40);
        p2.add(button2);

        button3 = new JButton("Hash");
        button3.addActionListener(this);
        button3.setBounds(130, 50, 100, 40);
        p2.add(button3);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);
        setVisible(true);// hiển thị cửa sổ

    }

   public static void main(String[] args) {
	   new Welcome();
   }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Symmetric")) {
            try {
                new SymmetricUI();
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        }

        if (e.getActionCommand().equalsIgnoreCase("ASymmetric")) {
            new ASymmetricUI();
        }

        if (e.getActionCommand().equalsIgnoreCase("Hash")){
            new HashUI();
        }

    }
}
