package Project.UI.SymmetricUI;

import Project.Crypto.Symmetric.JavaSymmetric;

import javax.crypto.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.security.*;

public class SymmetricUI extends JFrame implements ActionListener {

	JPanel p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12;
	JLabel message, keySize, key, filePath, algoes, modes, padding;
	JTextField txtMessage, txtKeySize, txtFilePath, txtEncryptFile, txtDecryptFile;
	JTextArea txtKey, txtEncrypted, txtDecrypted;
	JButton b1, b2, generateButton, showFileDialogButton, b3, b4;
	JRadioButton ecb, cbc, pcbc, ofb, cfb, ctr, pkcs, no, iso;
	JComboBox<String> algoCombo;
	DefaultComboBoxModel<String> algoName;
	JScrollPane paneKey, paneEncrypted, paneDecrypted, paneAlgo;
	ButtonGroup groupMode = new ButtonGroup();
	ButtonGroup groupPadding = new ButtonGroup();
	String encryptedMessage, decryptedMessage, keyS;
	JFileChooser fileDialog;
	FileNameExtensionFilter filter;
	File file, encryptFile, decryptFile;

	JavaSymmetric symmetric;

	public SymmetricUI() throws NoSuchAlgorithmException {
		// container
		Container pane = getContentPane();
		setTitle("Symmetric");
		JPanel master = new JPanel();
		int center = TitledBorder.CENTER;
		master.setLayout(new BoxLayout(master, BoxLayout.Y_AXIS));
		master.setBorder((BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "JAVA SYMMETRIC", center,
				TitledBorder.TOP)));

		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		p7 = new JPanel();

		p8 = new JPanel();
		p8.setBorder((BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Message", center,
				TitledBorder.TOP)));

		p9 = new JPanel();
		p9.setLayout(new BoxLayout(p9, BoxLayout.Y_AXIS));
		p9.setBorder((BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "File", center,
				TitledBorder.TOP)));

		p10 = new JPanel(new FlowLayout());
		p11 = new JPanel();
		p12 = new JPanel();

		master.add(p2);
		master.add(p3);
		master.add(p4);
		master.add(p5);
		master.add(p6);
		master.add(p7);
		master.add(p10);

		pane.add(master);

		// Panel 2
		message = new JLabel("Message:");
		message.setBounds(10, 50, 80, 25);
		txtMessage = new JTextField(30);
		txtMessage.setBounds(100, 20, 165, 25);

		filePath = new JLabel("File:");
		filePath.setBounds(10, 20, 80, 25);
		txtFilePath = new JTextField(30);
		txtFilePath.setBounds(100, 50, 165, 25);

		showFileDialogButton = new JButton("Open File");
		showFileDialogButton.addActionListener(this);

		p2.add(message);
		p2.add(txtMessage);
		p2.add(filePath);
		p2.add(txtFilePath);
		p2.add(showFileDialogButton);
		// ----------------------------------------------------------

		// Panel 4
		p4.setLayout(new FlowLayout());

		algoes = new JLabel("Algorithm:");
		algoName = new DefaultComboBoxModel<>();
		algoName.addElement("AES");
		algoName.addElement("DES");
		algoName.addElement("DESede");
		algoName.addElement("RC2");
		algoName.addElement("Blowfish");
		algoName.addElement("Twofish");
		algoName.addElement("RC4");
		algoName.addElement("RC5");
		algoName.addElement("Serpent");

		algoCombo = new JComboBox<>(algoName);
		algoCombo.addActionListener(this);
		paneAlgo = new JScrollPane(algoCombo);

		p4.add(algoes);
		p4.add(paneAlgo);

		modes = new JLabel("Mode: ");
		ecb = new JRadioButton("ECB");
		ecb.addActionListener(this);

		cbc = new JRadioButton("CBC");
		cbc.addActionListener(this);

		pcbc = new JRadioButton("PCBC");
		pcbc.addActionListener(this);

		ofb = new JRadioButton("OFB");
		ofb.addActionListener(this);

		cfb = new JRadioButton("CFB");
		cfb.addActionListener(this);

		ctr = new JRadioButton("CTR");
		ctr.addActionListener(this);

		groupMode.add(ecb);
		groupMode.add(cbc);
		groupMode.add(pcbc);
		groupMode.add(ofb);
		groupMode.add(cfb);
		groupMode.add(ctr);

		p4.add(modes);
		p4.add(ecb);
		p4.add(cbc);
		p4.add(pcbc);
		p4.add(ofb);
		p4.add(cfb);
		p4.add(ecb);
		p4.add(ctr);
		// ----------------------------------------------------------

		// Panel 5
		p5.setLayout(new FlowLayout());
		padding = new JLabel("Padding: ");

		pkcs = new JRadioButton("PKCS5PADDING");
		pkcs.addActionListener(this);

		no = new JRadioButton("NoPadding");
		no.addActionListener(this);
		no.setEnabled(false);

		iso = new JRadioButton("ISO10126PADDING");
		iso.addActionListener(this);

		groupPadding.add(pkcs);
		groupPadding.add(iso);
		groupPadding.add(no);

		p5.add(padding);
		p5.add(pkcs);
		p5.add(iso);
		p5.add(no);
		// ----------------------------------------------------------

		// Panel 6
		keySize = new JLabel("Key size: ");
		txtKeySize = new JTextField(10);
		p6.add(keySize);
		p6.add(txtKeySize);
		// ----------------------------------------------------------

		// Panel 7
		generateButton = new JButton("Generate Key");
		generateButton.addActionListener(this);
		generateButton.setBounds(130, 50, 100, 40);
		txtKey = new JTextArea(5, 20);
		txtKey.setLineWrap(true);
		txtKey.setWrapStyleWord(true);
		paneKey = new JScrollPane(txtKey);
		p7.add(generateButton);
		p7.add(key = new JLabel("Key:"));
		p7.add(paneKey, BorderLayout.CENTER);
		// ----------------------------------------------------------

		// Panel 10
		b1 = new JButton("Encrypt");
		b1.addActionListener(this);
		b1.setBounds(130, 50, 100, 40);
		p8.add(b1);
		txtEncrypted = new JTextArea(5, 20);
		txtEncrypted.setLineWrap(true);
		txtEncrypted.setWrapStyleWord(true);
		paneEncrypted = new JScrollPane(txtEncrypted);
		p8.add(paneEncrypted, BorderLayout.CENTER);

		b2 = new JButton("Decrypt");
		b2.addActionListener(this);
		b2.setBounds(130, 50, 100, 40);
		p8.add(b2);
		txtDecrypted = new JTextArea(5, 20);
		txtDecrypted.setLineWrap(true);
		txtDecrypted.setWrapStyleWord(true);
		paneDecrypted = new JScrollPane(txtDecrypted);
		p8.add(paneDecrypted, BorderLayout.CENTER);

		b3 = new JButton("Encrypt File");
		b3.addActionListener(this);
		b3.setBounds(130, 50, 100, 40);
		txtEncryptFile = new JTextField(20);
		txtEncryptFile.setBounds(100, 20, 165, 25);
		p11.add(txtEncryptFile);
		p11.add(b3);

		b4 = new JButton("Decrypt File");
		b4.addActionListener(this);
		b4.setBounds(130, 50, 100, 40);
		txtDecryptFile = new JTextField(20);
		txtDecryptFile.setBounds(100, 20, 165, 25);
		p12.add(txtDecryptFile);
		p12.add(b4);

		p9.add(p11);
		p9.add(p12);

		p10.add(p8);
		p10.add(p9);

		// ------------------------------------------------------

		pack();
		setSize(1100, 400);
		setVisible(true);// hiển thị cửa sổ

	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		new SymmetricUI();
	}

	public String selectedAlgo() {
		String algorithm = "";

		if (algoCombo.getSelectedIndex() != -1) {
			if (algoCombo.getItemAt(algoCombo.getSelectedIndex()).equalsIgnoreCase("AES")) {
				keySize.setText("Key size (128 , 192 , 256):");
				algorithm = algoCombo.getItemAt(algoCombo.getSelectedIndex());
			}

			if (algoCombo.getItemAt(algoCombo.getSelectedIndex()).equalsIgnoreCase("DES")) {
				keySize.setText("Key size (56):");
				algorithm = algoCombo.getItemAt(algoCombo.getSelectedIndex());
			}

			if (algoCombo.getItemAt(algoCombo.getSelectedIndex()).equalsIgnoreCase("RC2")) {
				keySize.setText("Key size (40 - 1024):");
				algorithm = algoCombo.getItemAt(algoCombo.getSelectedIndex());
			}

			if (algoCombo.getItemAt(algoCombo.getSelectedIndex()).equalsIgnoreCase("Blowfish")) {
				keySize.setText("Key size (32 , 448):");
				algorithm = algoCombo.getItemAt(algoCombo.getSelectedIndex());
			}

			if (algoCombo.getItemAt(algoCombo.getSelectedIndex()).equalsIgnoreCase("Twofish")) {
				keySize.setText("Key size (128 , 192 , 256):");
				algorithm = algoCombo.getItemAt(algoCombo.getSelectedIndex());
			}

			if (algoCombo.getItemAt(algoCombo.getSelectedIndex()).equalsIgnoreCase("Serpent")) {
				keySize.setText("Key size (128 , 192 , 256):");
				algorithm = algoCombo.getItemAt(algoCombo.getSelectedIndex());
			}

			if (algoCombo.getItemAt(algoCombo.getSelectedIndex()).equalsIgnoreCase("DESede")) {
				keySize.setText("Key size (112 , 168):");
				algorithm = algoCombo.getItemAt(algoCombo.getSelectedIndex());
			}

			if (algoCombo.getItemAt(algoCombo.getSelectedIndex()).equalsIgnoreCase("RC4")) {
				keySize.setText("Key size (40 - 1024):");
				algorithm = algoCombo.getItemAt(algoCombo.getSelectedIndex());
			}

			if (algoCombo.getItemAt(algoCombo.getSelectedIndex()).equalsIgnoreCase("RC5")) {
				keySize.setText("Key size (1 - 2024):");
				algorithm = algoCombo.getItemAt(algoCombo.getSelectedIndex());
			}

		}

		return algorithm;
	}

	public String selectPadding() {
		String padding = "";

		if (ctr.isSelected()) {
			no.setEnabled(true);
			pkcs.setEnabled(false);
			iso.setEnabled(false);

			if (no.isSelected()) {
				padding = no.getText();
			}

		} else {
			no.setEnabled(false);
			pkcs.setEnabled(true);
			iso.setEnabled(true);

			if (pkcs.isSelected()) {
				padding = pkcs.getText();
			}

			if (iso.isSelected()) {
				padding = iso.getText();
			}
		}

		return padding;
	}

	public String selectedModes() {
		String mode = "";
		String al = selectedAlgo();

		if (al.equalsIgnoreCase("Serpent") || al.equalsIgnoreCase("Twofish") || al.equalsIgnoreCase("RC5")) {
			pcbc.setEnabled(false);

			if (ecb.isSelected()) {
				mode = ecb.getText();
			}

			if (cbc.isSelected()) {
				mode = cbc.getText();
			}

			if (ofb.isSelected()) {
				mode = ofb.getText();
			}

			if (cfb.isSelected()) {
				mode = cfb.getText();
			}

			if (ctr.isSelected()) {
				mode = ctr.getText();
			}

		} else {
			pcbc.setEnabled(true);

			if (ecb.isSelected()) {
				mode = ecb.getText();
			}

			if (cbc.isSelected()) {
				mode = cbc.getText();
			}

			if (pcbc.isSelected()) {
				mode = pcbc.getText();
			}

			if (ofb.isSelected()) {
				mode = ofb.getText();
			}

			if (cfb.isSelected()) {
				mode = cfb.getText();
			}

			if (ctr.isSelected()) {
				mode = ctr.getText();
			}
		}

		return mode;
	}

	public void actionPerformed(ActionEvent e) {
		String algoKey = selectedAlgo();
		String mode = selectedModes();
		String padding = selectPadding();
		String algorithm = algoKey + "/" + mode + "/" + padding;

		fileDialog = new JFileChooser();
		fileDialog.setFileFilter(new FileNameExtensionFilter("doc", "doc"));
		fileDialog.setFileFilter(new FileNameExtensionFilter("pdf", "pdf"));
		fileDialog.setFileFilter(new FileNameExtensionFilter("docx", "docx"));
		fileDialog.setFileFilter(new FileNameExtensionFilter("xlsx", "xlsx"));
		fileDialog.setFileFilter(new FileNameExtensionFilter("txt", "txt"));
		fileDialog.setFileFilter(new FileNameExtensionFilter("text", "text"));
		fileDialog.setFileFilter(new FileNameExtensionFilter("jpg", "jpg"));
		fileDialog.setFileFilter(new FileNameExtensionFilter("png", "png"));

		if (e.getActionCommand().equalsIgnoreCase("Generate Key")) {

			if (txtKeySize.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter a key size!!!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				int keySize = Integer.parseInt(txtKeySize.getText());
				try {
					symmetric = new JavaSymmetric(algorithm, algoKey, keySize);
				} catch (NoSuchAlgorithmException | NoSuchProviderException | NumberFormatException ex) {

					ex.printStackTrace();
				}
				keyS = symmetric.getKeyStringForm(symmetric.getKey());
				txtKey.setText(keyS);
			}
		}

		if (e.getActionCommand().equalsIgnoreCase("Open File")) {
			int returnVal = fileDialog.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = fileDialog.getSelectedFile();
				txtFilePath.setText(file.getPath());
			} else {
				txtFilePath.setText("Open command cancelled by user.");
			}

		}

		if (e.getActionCommand().equalsIgnoreCase("Encrypt")) {

			if (txtMessage.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter a message!!!", "Error", JOptionPane.ERROR_MESSAGE);
			} else if (txtKey.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "There is no key!!!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					encryptedMessage = symmetric.encrypt(txtMessage.getText());
					txtEncrypted.setText(encryptedMessage);

				} catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
						| IllegalBlockSizeException | BadPaddingException | IOException
						| InvalidAlgorithmParameterException | NoSuchProviderException ex) {
					ex.printStackTrace();
				}
			}
		}

		if (e.getActionCommand().equalsIgnoreCase("Encrypt File")) {
			if (txtFilePath.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter a File!!!", "Error", JOptionPane.ERROR_MESSAGE);
			} else if (txtKey.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "There is no key!!!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {

				try {
					String source = file.getPath();
					int userSelection = fileDialog.showSaveDialog(this);
					if (userSelection == JFileChooser.APPROVE_OPTION) {
						encryptFile = fileDialog.getSelectedFile();

					}
					symmetric.encryptFile(source,
							encryptFile.getPath() + "." + fileDialog.getFileFilter().getDescription());
					txtEncryptFile.setText(encryptFile.getAbsolutePath());

				} catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
						| IllegalBlockSizeException | BadPaddingException | IOException
						| InvalidAlgorithmParameterException ex) {
					ex.printStackTrace();
				}
			}
		}

		if (e.getActionCommand().equalsIgnoreCase("Decrypt")) {

			if (txtEncrypted.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Encrypt is empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {

				try {
					decryptedMessage = symmetric.decrypt(txtEncrypted.getText());
					txtDecrypted.setText(decryptedMessage);

				} catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
						| IllegalBlockSizeException | BadPaddingException | IOException
						| InvalidAlgorithmParameterException ex) {
					ex.printStackTrace();
				}
			}

		}

		if (e.getActionCommand().equalsIgnoreCase("Decrypt File")) {
			if (txtEncryptFile.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Please enter a message!!!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				try {

					String source =  txtEncryptFile.getText() + "." + fileDialog.getFileFilter().getDescription() ;
					int userSelection = fileDialog.showSaveDialog(this);
					if (userSelection == JFileChooser.APPROVE_OPTION) {
						decryptFile = fileDialog.getSelectedFile();
					}
					symmetric.decryptFile(source,
							decryptFile.getPath() + "." + fileDialog.getFileFilter().getDescription());
					txtDecryptFile.setText(decryptFile.getAbsolutePath());

				} catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
						| IllegalBlockSizeException | BadPaddingException | IOException
						| InvalidAlgorithmParameterException ex) {
					ex.printStackTrace();
				}

			}
		}
	}

}
