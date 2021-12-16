package Project.UI.HashUI;

import Project.Crypto.Hash.Hash;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class HashUI extends JFrame {

	JPanel p1, p2;
	JLabel message, file, hashed;
	JTextArea txtMessage, txtHash, txtFile;
	JTextField filePath;
	JButton generate, check, openFile;
	JScrollPane paneMessge, paneHashedMessage, paneFile;
	JComboBox<String> comboBox;
	DefaultComboBoxModel<String> algoName;
	JFileChooser fileChooser;
	File path;
	Hash h;
	String algo = "";

	public HashUI() {
		setTitle("Hash");
		Container pane = getContentPane();

		p1 = new JPanel();

		p1.add(new HashMessage());

		pane.add(p1);

		pack();
		setVisible(true);
		setSize(680, 550);

	}

	public class HashMessage extends JPanel {
		public HashMessage() {
			setBorder(new EmptyBorder(8, 8, 8, 8));
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;

			AlgorithmSelect algorithmSelect = new AlgorithmSelect();
			algorithmSelect.setBorder(new CompoundBorder(new TitledBorder("Select"), new EmptyBorder(4, 4, 4, 4)));
			add(algorithmSelect, gbc);
			gbc.gridy++;

			Message message1 = new Message();
			message1.setBorder(new CompoundBorder(new TitledBorder("Message"), new EmptyBorder(4, 4, 4, 4)));
			add(message1, gbc);

			gbc.gridy++;
			FileCheck fc = new FileCheck();
			fc.setBorder(new CompoundBorder(new TitledBorder("File"), new EmptyBorder(4, 4, 4, 4)));
			add(fc, gbc);

		}
	}

	public class FileCheck extends JPanel implements ActionListener {

		public FileCheck() {
			JPanel f = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			f.add(file = new JLabel("File:"), gbc);

			gbc.gridx++;
			gbc.weightx = 1;
			gbc.anchor = GridBagConstraints.EAST;
			f.add(filePath = new JTextField(20), gbc);

			gbc.gridx++;
			gbc.weightx = 0;
			gbc.anchor = GridBagConstraints.EAST;
			fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new FileNameExtensionFilter(".doc", "doc"));
			fileChooser.setFileFilter(new FileNameExtensionFilter(".pdf", "pdf"));
			fileChooser.setFileFilter(new FileNameExtensionFilter(".docx", "docx"));
			fileChooser.setFileFilter(new FileNameExtensionFilter(".xlsx", "xlsx"));
			fileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
			fileChooser.setFileFilter(new FileNameExtensionFilter(".text", "text"));
			fileChooser.setFileFilter(new FileNameExtensionFilter(".jpg", "jpg"));
			fileChooser.setFileFilter(new FileNameExtensionFilter(".png", "png"));
			f.add(openFile = new JButton("Open"), gbc);
			openFile.addActionListener(this);

			gbc.gridx = 0;
			gbc.gridy += 2;
			f.add(check = new JButton("Check"), gbc);
			check.addActionListener(this);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.weightx = 1;
			gbc.fill = 1;
			txtFile = new JTextArea(10, 20);
			txtFile.setLineWrap(true);
			txtFile.setWrapStyleWord(true);
			f.add(paneFile = new JScrollPane(txtFile), gbc);

			setLayout(new BorderLayout());
			add(f);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equalsIgnoreCase("Open")) {
				int returnVal = fileChooser.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					path = fileChooser.getSelectedFile();
					filePath.setText(path.getPath());
				} else {
					filePath.setText("Open command cancelled by user.");
				}
			}

			if (e.getActionCommand().equalsIgnoreCase("Check")) {
				if (filePath.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "File is empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (algo.equals("") || algo.equalsIgnoreCase("None")) {
					JOptionPane.showMessageDialog(null, "Please choose an algorithm!!!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {

					try {
						txtFile.setText(h.hashFile(path.getAbsolutePath()));
					} catch (NoSuchAlgorithmException | IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	public class AlgorithmSelect extends JPanel implements ActionListener {
		public AlgorithmSelect() {

			JPanel m = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;

			algoName = new DefaultComboBoxModel<>();
			algoName.addElement("None");
			algoName.addElement("MD5");
			algoName.addElement("SHA-1");
			algoName.addElement("SHA-256");
			comboBox = new JComboBox<>(algoName);
			m.add(comboBox, gbc);
			comboBox.addActionListener(this);

			setLayout(new BorderLayout());
			add(m);

		}

		public String selectAlgo() {
			String res = "";
			if (comboBox.getSelectedIndex() != -1) {

				if (comboBox.getItemAt(comboBox.getSelectedIndex()).equalsIgnoreCase("MD5")) {
					res = comboBox.getItemAt(comboBox.getSelectedIndex());
				}

				if (comboBox.getItemAt(comboBox.getSelectedIndex()).equalsIgnoreCase("SHA-1")) {
					res = comboBox.getItemAt(comboBox.getSelectedIndex());
				}

				if (comboBox.getItemAt(comboBox.getSelectedIndex()).equalsIgnoreCase("SHA-256")) {
					res = comboBox.getItemAt(comboBox.getSelectedIndex());
				}

				if (comboBox.getItemAt(comboBox.getSelectedIndex()).equalsIgnoreCase("None")) {
					res = comboBox.getItemAt(comboBox.getSelectedIndex());
				}

			}

			return res;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			algo = selectAlgo();
			h = new Hash(algo);

		}
	}

	public class Message extends JPanel implements ActionListener {

		public Message() {
			JPanel m = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			m.add(message = new JLabel("Message:"), gbc);

			gbc.gridy++;
			txtMessage = new JTextArea(10, 20);
			txtMessage.setLineWrap(true);
			txtMessage.setWrapStyleWord(true);
			m.add(paneMessge = new JScrollPane(txtMessage), gbc);

			gbc.gridx += 2;
			gbc.weighty = 1;
			gbc.weightx = 1;
			generate = new JButton("Generate");
			generate.setBounds(269, 99, 108, 31);
			m.add(generate, gbc);
			generate.addActionListener(this);

			gbc.gridy = 0;
			gbc.gridx += 9;
			gbc.anchor = GridBagConstraints.WEST;
			m.add(hashed = new JLabel("Hash:"), gbc);

			gbc.gridy++;
			gbc.anchor = GridBagConstraints.EAST;
			txtHash = new JTextArea(10, 20);
			txtHash.setLineWrap(true);
			txtHash.setWrapStyleWord(true);
			m.add(paneHashedMessage = new JScrollPane(txtHash), gbc);

			setLayout(new BorderLayout());
			add(m);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equalsIgnoreCase("Generate")) {
				if (txtMessage.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Message is empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (algo.equals("") || algo.equalsIgnoreCase("None")) {
					JOptionPane.showMessageDialog(null, "Please choose an algorithm!!!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						txtHash.setText(h.hashString(txtMessage.getText()));
					} catch (NoSuchAlgorithmException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		new HashUI();
	}
}
