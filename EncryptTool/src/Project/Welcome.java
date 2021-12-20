package Project;

import Project.UI.ASymmetricUI.ASymmetricUI;
import Project.UI.HashUI.HashUI;
import Project.UI.SymmetricUI.SymmetricUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;

public class Welcome extends JFrame implements ActionListener, Runnable {

	JPanel p1, p2;
	JLabel l1;
	JButton button1, button2, button3;

	public Welcome() {
		run();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("Label.font", UIManager.getFont("Label.font").deriveFont(Font.BOLD, 14f));
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// container
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
		button1.addActionListener((ActionListener) this);
		button1.setBounds(130, 50, 100, 40);
		p2.add(button1);

		button2 = new JButton("ASymmetric");
		button2.addActionListener(this);
		button2.setBounds(130, 50, 100, 40);
		p2.add(button2);

		button3 = new JButton("Hash");
		button3.addActionListener((ActionListener) this);
		button3.setBounds(130, 50, 100, 40);
		p2.add(button3);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);

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

		if (e.getActionCommand().equalsIgnoreCase("Hash")) {
			new HashUI();
		}

	}
	

	public static void main(String[] args) {
		new Welcome();
	}

}
