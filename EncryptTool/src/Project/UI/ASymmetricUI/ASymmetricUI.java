package Project.UI.ASymmetricUI;

import Project.Crypto.ASymmetric.JavaASymmetric;
import Project.Crypto.ASymmetric.JavaHybrid;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ASymmetricUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel p1, p2, p3;
	JLabel keySize, publicKey, privateKey, message, file, encryptMessage, decryptMessage, messageHybrid, encodedHybrid,
			decodedHybrid, encryptFilePath, decryptFilePath, modes, symmetric, secretKey, symmetricMode,
			symmetricPadding, symmetricAlgo;
	JTextField txtKey, txtPrivate, txtPublic, txtMessage, txtFile, txtEncryptPath, txtHybridMessage, txtHybridFile,
			txtDecryptPath, txtSecretKeySize, txtSecretKey, txtHybridEncryptFile, txtHybridDecryptFile;
	JTextArea txtEncrypt, txtDecrypt, txtHybridEncrypt, txtHybridDecrypt;
	JButton generateButton, secretKeyGen, openFile, openHybridFile, encryptButton, decryptButton, encryptFileButton,
			decryptFileButton, encryptHybrid, decryptHybrid, encryptHybridFile, decryptHybridFile;
	JTabbedPane tabbedPane;
	JFileChooser fileDialog;
	JRadioButton ecb;
	JScrollPane paneEncrypt, paneDecrypt;
	DefaultComboBoxModel<String> algoName, modesName, paddingName;
	JComboBox<String> combox, comboMode, comboPadding;
	File pathFile, encryptFile, decryptFile;
	String ec = "";
	String algo = "RSA";
	JavaASymmetric aSymmetric;
	JavaHybrid javaHybrid;

	public ASymmetricUI() {
		// container
		setTitle("ASymmetric");
		Container pane = getContentPane();

		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();

		p1.add(new RSATab());
		p2.add(new HybridTab());
		p3.add(new FormPane());

		tabbedPane = new JTabbedPane();

		tabbedPane.addTab("RSA", p1);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		tabbedPane.addTab("Hybrid", p2);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		tabbedPane.addTab("Key Generate", p3);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_2);

		pane.add(tabbedPane);

		pack();
		setBounds(100, 100, 1000, 436);
		setVisible(true);// hiển thị cửa sổ
	}

	public class RSATab extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public RSATab() {
			setBorder(new EmptyBorder(8, 8, 8, 8));
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;

			InsertFileMessagePanel insertFileMessagePanel = new InsertFileMessagePanel();
			insertFileMessagePanel
					.setBorder(new CompoundBorder(new TitledBorder("Insert"), new EmptyBorder(4, 4, 4, 4)));
			add(insertFileMessagePanel, gbc);

			gbc.gridy += 2;
			Options options = new Options();
			options.setBorder(new CompoundBorder(new TitledBorder("Options"), new EmptyBorder(4, 4, 4, 4)));
			add(options, gbc);

			gbc.gridy += 2;
			Encrypt_Decrypt encrypt_decrypt = new Encrypt_Decrypt();
			encrypt_decrypt
					.setBorder(new CompoundBorder(new TitledBorder("Encrypt_Decrypt"), new EmptyBorder(4, 4, 4, 4)));
			add(encrypt_decrypt, gbc);

			gbc.gridx++;
			gbc.weightx = 1;
			Encrypt_DecryptFile f = new Encrypt_DecryptFile();
			f.setBorder(new CompoundBorder(new TitledBorder("Encrypt_Decrypt File"), new EmptyBorder(4, 4, 4, 4)));
			add(f, gbc);
		}
	}

	public class HybridTab extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public HybridTab() {
			setBorder(new EmptyBorder(8, 8, 8, 8));
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;

			Hybrid_Insert hybrid_insert = new Hybrid_Insert();
			hybrid_insert.setBorder(new CompoundBorder(new TitledBorder("Insert"), new EmptyBorder(4, 4, 4, 4)));
			add(hybrid_insert, gbc);
			gbc.gridy++;

			Encrypt_DecryptHybrid encrypt_decryptHybrid = new Encrypt_DecryptHybrid();
			encrypt_decryptHybrid
					.setBorder(new CompoundBorder(new TitledBorder("Message"), new EmptyBorder(4, 4, 4, 4)));
			add(encrypt_decryptHybrid, gbc);
			gbc.gridy++;

			Encrypt_DecryptHybridFile encryptDecryptHybridFile = new Encrypt_DecryptHybridFile();
			encryptDecryptHybridFile
					.setBorder(new CompoundBorder(new TitledBorder("File"), new EmptyBorder(4, 4, 4, 4)));
			add(encryptDecryptHybridFile, gbc);

		}
	}

	public class FormPane extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public FormPane() {
			setBorder(new EmptyBorder(8, 8, 8, 8));
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;

			InsertKeyPanel insertPane = new InsertKeyPanel();
			insertPane.setBorder(new CompoundBorder(new TitledBorder("Key size"), new EmptyBorder(4, 4, 4, 4)));
			add(insertPane, gbc);
			gbc.gridy++;

			InsertSecretKeyPanel secretKeyPanel = new InsertSecretKeyPanel();
			secretKeyPanel
					.setBorder(new CompoundBorder(new TitledBorder("Key Symmetric"), new EmptyBorder(4, 4, 4, 4)));
			add(secretKeyPanel, gbc);
			gbc.gridy++;

			gbc.weightx = 1;
			GenerateKeyPanel generate = new GenerateKeyPanel();
			generate.setBorder(new CompoundBorder(new TitledBorder("Key Pair"), new EmptyBorder(4, 4, 4, 4)));
			add(generate, gbc);

		}
	}

	public class Hybrid_Insert extends JPanel implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Hybrid_Insert() {
			JPanel hInsert = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			hInsert.add(messageHybrid = new JLabel("Message:"), gbc);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.EAST;
			gbc.weightx = 1;
			hInsert.add(txtHybridMessage = new JTextField(40), gbc);

			gbc.gridx = 0;
			gbc.gridy++;
			gbc.anchor = GridBagConstraints.EAST;
			hInsert.add(file = new JLabel("File: "), gbc);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.WEST;
			hInsert.add(txtHybridFile = new JTextField(40), gbc);

			gbc.gridx++;
			fileDialog = new JFileChooser();
			fileDialog.setFileFilter(new FileNameExtensionFilter(".doc", "doc"));
			fileDialog.setFileFilter(new FileNameExtensionFilter(".pdf", "pdf"));
			fileDialog.setFileFilter(new FileNameExtensionFilter(".docx", "docx"));
			fileDialog.setFileFilter(new FileNameExtensionFilter(".xlsx", "xlsx"));
			fileDialog.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
			fileDialog.setFileFilter(new FileNameExtensionFilter(".text", "text"));
			fileDialog.setFileFilter(new FileNameExtensionFilter(".jpg", "jpg"));
			fileDialog.setFileFilter(new FileNameExtensionFilter(".png", "png"));
			hInsert.add(openHybridFile = new JButton("Open"), gbc);
			openHybridFile.addActionListener(this);

			setLayout(new BorderLayout());
			add(hInsert);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equalsIgnoreCase("Open")) {
				int returnVal = fileDialog.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					pathFile = fileDialog.getSelectedFile();
					txtHybridFile.setText(pathFile.getPath());
				} else {
					txtHybridFile.setText("Open command cancelled by user.");
				}
			}
		}
	}

	public class Encrypt_DecryptHybrid extends JPanel implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Encrypt_DecryptHybrid() {
			JPanel eDHybrid = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			eDHybrid.add(encodedHybrid = new JLabel("Encrypt: "), gbc);

			gbc.weightx = 1;
			gbc.gridx++;
			gbc.anchor = GridBagConstraints.EAST;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			txtHybridEncrypt = new JTextArea(10, 15);
			txtHybridEncrypt.setWrapStyleWord(true);
			txtHybridEncrypt.setLineWrap(true);
			eDHybrid.add(txtHybridEncrypt , gbc);

			gbc.weightx = 1;
			gbc.gridx++;
			gbc.anchor = GridBagConstraints.EAST;
			eDHybrid.add(encryptHybrid = new JButton("Encrypt"), gbc);
			encryptHybrid.addActionListener(this);

			gbc.weightx = 1;
			gbc.gridx++;
			gbc.anchor = GridBagConstraints.EAST;
			eDHybrid.add(decodedHybrid = new JLabel("Decrypt: "), gbc);

			gbc.weightx = 1;
			gbc.gridx++;
			gbc.anchor = GridBagConstraints.EAST;
			txtHybridDecrypt = new JTextArea(10, 15);
			txtHybridDecrypt.setWrapStyleWord(true);
			txtHybridDecrypt.setLineWrap(true);
			eDHybrid.add(txtHybridDecrypt, gbc);

			gbc.weightx = 1;
			gbc.gridx++;
			gbc.anchor = GridBagConstraints.EAST;
			eDHybrid.add(decryptHybrid = new JButton("Decrypt"), gbc);
			decryptHybrid.addActionListener(this);

			setLayout(new BorderLayout());
			add(eDHybrid);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equalsIgnoreCase("Encrypt")) {
				if (txtHybridMessage.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please enter a message!!!", "Error",
							JOptionPane.ERROR_MESSAGE);

				} else if (txtPrivate.getText().equals("") || txtPublic.getText().equals("")
						|| txtSecretKey.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "There is no key!!!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						String encryptM = javaHybrid.encryptUsingSymmetric(txtHybridMessage.getText());
						txtHybridEncrypt.setText(encryptM);
					} catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException
							| BadPaddingException | InvalidAlgorithmParameterException | InvalidKeyException ex) {
						ex.printStackTrace();
					}
				}
			}

			if (e.getActionCommand().equalsIgnoreCase("Decrypt")) {
				if (txtHybridEncrypt.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Encrypt is empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						txtHybridDecrypt.setText(javaHybrid.decryptUsingSymmetric(txtHybridEncrypt.getText()));
					} catch (IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException
							| InvalidKeyException | NoSuchAlgorithmException | InvalidAlgorithmParameterException ex) {
						ex.printStackTrace();
					}

				}
			}
		}
	}

	public class Encrypt_DecryptHybridFile extends JPanel implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Encrypt_DecryptHybridFile() {
			JPanel eDFileHybrid = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			eDFileHybrid.add(encodedHybrid = new JLabel("Encrypt: "), gbc);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.weightx = 1;
			eDFileHybrid.add(txtHybridEncryptFile = new JTextField(40), gbc);

			gbc.gridx++;
			eDFileHybrid.add(encryptHybridFile = new JButton("Encrypt"), gbc);
			encryptHybridFile.addActionListener(this);

			gbc.gridx = 0;
			gbc.gridy++;
			gbc.anchor = GridBagConstraints.EAST;
			eDFileHybrid.add(decodedHybrid = new JLabel("Decrypt: "), gbc);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.weightx = 1;
			eDFileHybrid.add(txtHybridDecryptFile = new JTextField(40), gbc);

			gbc.gridx++;
			eDFileHybrid.add(decryptHybridFile = new JButton("Decrypt"), gbc);
			decryptHybridFile.addActionListener(this);

			setLayout(new BorderLayout());
			add(eDFileHybrid);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equalsIgnoreCase("Encrypt")) {

				if (txtHybridFile.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "There is no File!!!", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (txtPrivate.getText().equals("") || txtPublic.getText().equals("")
						|| txtSecretKey.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "There is no key!!!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {

					try {
						String source = pathFile.getPath();
						int userSelection = fileDialog.showSaveDialog(this);
						if (userSelection == JFileChooser.APPROVE_OPTION) {
							encryptFile = fileDialog.getSelectedFile();

						}
						javaHybrid.encryptFileUsingSymmetric(source,
								encryptFile.getPath() + "." + fileDialog.getFileFilter().getDescription());
						txtHybridEncryptFile.setText(
								encryptFile.getAbsolutePath() + "." + fileDialog.getFileFilter().getDescription());

					} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
							| BadPaddingException | InvalidKeyException | IOException
							| InvalidAlgorithmParameterException ex) {
						ex.printStackTrace();
					}
				}
			}

			if (e.getActionCommand().equalsIgnoreCase("Decrypt")) {
				if (txtHybridEncryptFile.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "There is no Encrypt File!!!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						String source = txtHybridEncryptFile.getText();
						int userSelection = fileDialog.showSaveDialog(this);
						if (userSelection == JFileChooser.APPROVE_OPTION) {
							decryptFile = fileDialog.getSelectedFile();
						}
						javaHybrid.decryptFileUsingSymmetric(source,
								decryptFile.getPath() + "." + fileDialog.getFileFilter().getDescription());
						txtHybridDecryptFile.setText(decryptFile.getAbsolutePath());

					} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
							| BadPaddingException | InvalidKeyException | IOException
							| InvalidAlgorithmParameterException ex) {
						ex.printStackTrace();
					}

				}
			}
		}
	}

	public class Encrypt_DecryptFile extends JPanel implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Encrypt_DecryptFile() {
			JPanel rs = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			rs.add(encryptFilePath = new JLabel("Encrypt: "), gbc);

			gbc.gridy++;
			gbc.gridx = 0;
			rs.add(decryptFilePath = new JLabel("Decrypt:"), gbc);

			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.weightx = 0.5;
			rs.add(txtEncryptPath = new JTextField(20), gbc);

			gbc.gridy++;
			gbc.gridx = 1;
			rs.add(txtDecryptPath = new JTextField(20), gbc);

			gbc.gridx++;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			rs.add(encryptFileButton = new JButton("Encrypt"), gbc);
			encryptFileButton.addActionListener(this);

			gbc.gridy++;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.weightx = 1;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			rs.add(decryptFileButton = new JButton("Decrypt"), gbc);
			decryptFileButton.addActionListener(this);

			setLayout(new BorderLayout());
			add(rs);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equalsIgnoreCase("Encrypt")) {
				if (txtFile.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "There is no File!!!", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (txtPrivate.getText().equals("") || txtPublic.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "There is no key!!!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						String source = pathFile.getPath();
						int userSelection = fileDialog.showSaveDialog(this);
						if (userSelection == JFileChooser.APPROVE_OPTION) {
							encryptFile = fileDialog.getSelectedFile();

						}

						if (new File(source).length() > 117) {
							JOptionPane.showMessageDialog(null, "Data must not be 117 bytes long", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							aSymmetric.encryptFile(source,
									encryptFile.getPath() + "." + fileDialog.getFileFilter().getDescription());
							txtEncryptPath.setText(
									encryptFile.getAbsolutePath() + "." + fileDialog.getFileFilter().getDescription());
						}

					} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
							| BadPaddingException | InvalidKeyException | IOException ex) {
						ex.printStackTrace();
					}
				}
			}

			if (e.getActionCommand().equalsIgnoreCase("Decrypt")) {
				if (txtEncryptPath.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "There is no Encrypt File!!!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						String source = txtEncryptPath.getText();
						int userSelection = fileDialog.showSaveDialog(this);
						if (userSelection == JFileChooser.APPROVE_OPTION) {
							decryptFile = fileDialog.getSelectedFile();
						}
						aSymmetric.decryptFile(source,
								decryptFile.getPath() + "." + fileDialog.getFileFilter().getDescription());
						txtDecryptPath.setText(decryptFile.getAbsolutePath());

					} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
							| BadPaddingException | InvalidKeyException | IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	public class Options extends JPanel implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Options() {
			JPanel op = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			op.add(modes = new JLabel("Mode: "), gbc);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.WEST;
			op.add(ecb = new JRadioButton("ECB"), gbc);
			ecb.addActionListener(this);

			setLayout(new BorderLayout());
			add(op);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (ecb.isSelected()) {
				ec = "ECB";
				algo += "/" + ec + "/" + "PKCS1Padding";
			} else {
				algo = "RSA";
			}

		}
	}

	public class Encrypt_Decrypt extends JPanel implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Encrypt_Decrypt() {
			JPanel result = new JPanel();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			result.add(encryptMessage = new JLabel("Encrypt"), gbc);

			gbc.gridx++;
			gbc.gridy = 0;
			gbc.weighty = 1;
			gbc.anchor = GridBagConstraints.EAST;
			txtEncrypt = new JTextArea(10, 10);
			txtEncrypt.setLineWrap(true);
			txtEncrypt.setWrapStyleWord(true);
			paneEncrypt = new JScrollPane(txtEncrypt);
			result.add(paneEncrypt, gbc);

			gbc.gridx++;
			gbc.gridy = 0;
			gbc.weighty = 1;
			gbc.anchor = GridBagConstraints.EAST;
			result.add(encryptButton = new JButton("Encrypt"), gbc);
			encryptButton.addActionListener(this);

			gbc.gridx = 0;
			gbc.gridy++;
			gbc.anchor = GridBagConstraints.EAST;
			result.add(decryptMessage = new JLabel("Decrypt"), gbc);

			gbc.gridx = 0;
			gbc.gridy++;
			gbc.anchor = GridBagConstraints.EAST;
			txtDecrypt = new JTextArea(10, 10);
			txtDecrypt.setLineWrap(true);
			txtDecrypt.setWrapStyleWord(true);
			result.add(paneDecrypt = new JScrollPane(txtDecrypt), gbc);

			gbc.gridx = 0;
			gbc.gridy++;
			gbc.weighty = 1;
			gbc.anchor = GridBagConstraints.EAST;
			result.add(decryptButton = new JButton("Decrypt"), gbc);
			decryptButton.addActionListener(this);

			setLayout(new BorderLayout());
			add(result);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equalsIgnoreCase("Encrypt")) {
				if (txtMessage.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Message is empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
				} else if (txtPrivate.getText().equals("") || txtPublic.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "There is no key!!!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						String encryptedMessge = aSymmetric.encrypt(txtMessage.getText());
						txtEncrypt.setText(encryptedMessge);
					} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
							| BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException ex) {
						ex.printStackTrace();
					}
				}
			}

			if (e.getActionCommand().equalsIgnoreCase("Decrypt")) {
				if (txtEncrypt.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Encrypt is empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						String decryptedMessage = aSymmetric.decrypt(txtEncrypt.getText());
						txtDecrypt.setText(decryptedMessage);
					} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
							| BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	public class InsertFileMessagePanel extends JPanel implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InsertFileMessagePanel() {
			JPanel result = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			result.add(message = new JLabel("Message:"), gbc);

			gbc.gridy++;
			gbc.gridx = 0;
			result.add(file = new JLabel("File:"), gbc);

			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.weightx = 0.5;
			result.add(txtMessage = new JTextField(40), gbc);

			gbc.gridy++;
			gbc.gridx = 1;
			result.add(txtFile = new JTextField(40), gbc);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.EAST;
			result.add(openFile = new JButton("Open"), gbc);
			openFile.addActionListener(this);

			setLayout(new BorderLayout());
			add(result);

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equalsIgnoreCase("Open")) {
				int returnVal = fileDialog.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					pathFile = fileDialog.getSelectedFile();
					txtFile.setText(pathFile.getPath());
				} else {
					txtFile.setText("Open command cancelled by user.");
				}
			}

		}
	}

	public class InsertSecretKeyPanel extends JPanel implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InsertSecretKeyPanel() {
			JPanel r = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.WEST;
			r.add(symmetricAlgo = new JLabel("Algorithm: "), gbc);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.weightx = 1;
			algoName = new DefaultComboBoxModel<String>();
			algoName.addElement("AES");
			algoName.addElement("DES");
			algoName.addElement("DESede");
			algoName.addElement("RC2");
			algoName.addElement("Blowfish");
			combox = new JComboBox<String>(algoName);
			combox.addActionListener(this);
			r.add(combox, gbc);

			gbc.gridx++;
			gbc.weightx = 1;
			gbc.fill = GridBagConstraints.BOTH;
			r.add(symmetricMode = new JLabel("Modes: "), gbc);

			gbc.gridx++;
			gbc.weightx = 1;
			modesName = new DefaultComboBoxModel<>();
			modesName.addElement("ECB");
			modesName.addElement("CBC");
			modesName.addElement("PCBC");
			modesName.addElement("OFB");
			modesName.addElement("CFB");
			comboMode = new JComboBox<>(modesName);
			comboMode.addActionListener(this);
			r.add(comboMode, gbc);

			gbc.gridx++;
			r.add(symmetricPadding = new JLabel("Padding: "), gbc);

			gbc.gridx++;
			paddingName = new DefaultComboBoxModel<>();
			paddingName.addElement("PKCS5PADDING");
			paddingName.addElement("NoPadding");
			paddingName.addElement("ISO10126PADDING");
			comboPadding = new JComboBox<>(paddingName);
			r.add(comboPadding, gbc);

			gbc.gridx = 0;
			gbc.gridy += 2;
			gbc.weightx = 1;
			gbc.anchor = GridBagConstraints.WEST;
			r.add(symmetric = new JLabel("Key size"), gbc);

			gbc.gridx++;
			gbc.weightx = 2;
			gbc.anchor = GridBagConstraints.WEST;
			r.add(txtSecretKeySize = new JTextField(20), gbc);

			gbc.gridx++;
			gbc.weightx = 2;
			gbc.anchor = GridBagConstraints.WEST;
			r.add(secretKeyGen = new JButton("Secret Keys"), gbc);
			secretKeyGen.addActionListener(this);

			setLayout(new BorderLayout());
			add(r);
		}

		public String selectMode() {
			String mode = "";
			if (comboMode.getSelectedIndex() != -1) {
				
				if (comboMode.getItemAt(comboMode.getSelectedIndex()).equalsIgnoreCase("ECB")) {
					mode = comboMode.getItemAt(comboMode.getSelectedIndex());
				}

				if (comboMode.getItemAt(comboMode.getSelectedIndex()).equalsIgnoreCase("CBC")) {
					mode = comboMode.getItemAt(comboMode.getSelectedIndex());
				}

				if (comboMode.getItemAt(comboMode.getSelectedIndex()).equalsIgnoreCase("PCBC")) {
					mode = comboMode.getItemAt(comboMode.getSelectedIndex());
				}

				if (comboMode.getItemAt(comboMode.getSelectedIndex()).equalsIgnoreCase("OFB")) {
					mode = comboMode.getItemAt(comboMode.getSelectedIndex());
				}

				if (comboMode.getItemAt(comboMode.getSelectedIndex()).equalsIgnoreCase("CFB")) {
					mode = comboMode.getItemAt(comboMode.getSelectedIndex());
				}

			}

			return mode;
		}

		public String selectPadding() {
			String padding = "";

			if (comboPadding.getSelectedIndex() != -1) {
				if (comboPadding.getItemAt(comboPadding.getSelectedIndex()).equalsIgnoreCase("PKCS5PADDING")) {
					padding = comboPadding.getItemAt(comboPadding.getSelectedIndex());
				}

				if (comboPadding.getItemAt(comboPadding.getSelectedIndex()).equalsIgnoreCase("NoPadding")) {
					padding = comboPadding.getItemAt(comboPadding.getSelectedIndex());
				}

				if (comboPadding.getItemAt(comboPadding.getSelectedIndex()).equalsIgnoreCase("ISO10126PADDING")) {
					padding = comboPadding.getItemAt(comboPadding.getSelectedIndex());
				}

			}

			return padding;
		}

		public String selectedAlgo() {
			String algorithm = "";

			if (combox.getSelectedIndex() != -1) {
				if (combox.getItemAt(combox.getSelectedIndex()).equalsIgnoreCase("AES")) {
					symmetric.setText("Key size (128 , 192 , 256):");
					algorithm = combox.getItemAt(combox.getSelectedIndex());
				}

				if (combox.getItemAt(combox.getSelectedIndex()).equalsIgnoreCase("DES")) {
					symmetric.setText("Key size (56):");
					algorithm = combox.getItemAt(combox.getSelectedIndex());
				}

				if (combox.getItemAt(combox.getSelectedIndex()).equalsIgnoreCase("RC2")) {
					symmetric.setText("Key size (40 - 1024):");
					algorithm = combox.getItemAt(combox.getSelectedIndex());
				}

				if (combox.getItemAt(combox.getSelectedIndex()).equalsIgnoreCase("Blowfish")) {
					symmetric.setText("Key size (32 , 448):");
					algorithm = combox.getItemAt(combox.getSelectedIndex());
				}

				if (combox.getItemAt(combox.getSelectedIndex()).equalsIgnoreCase("DESede")) {
					symmetric.setText("Key size (112 , 168):");
					algorithm = combox.getItemAt(combox.getSelectedIndex());
				}

			}

			return algorithm;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			String algoSymmetric = selectedAlgo();
			String modeSymmetric = selectMode();
			String paddingSymmetric = selectPadding();

			String k = algoSymmetric + "/" + modeSymmetric + "/" + paddingSymmetric;

			if (e.getActionCommand().equalsIgnoreCase("Secret Keys")) {

				if (txtSecretKeySize.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Key size is empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					int keySize = Integer.parseInt(txtSecretKeySize.getText());
					try {
						javaHybrid = new JavaHybrid(aSymmetric, keySize, algoSymmetric, k);
						txtSecretKey.setText(javaHybrid.getKeySymmetric());
					} catch (NoSuchAlgorithmException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	public class InsertKeyPanel extends JPanel implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InsertKeyPanel() {
			JPanel insert = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			insert.add(keySize = new JLabel("RSA Key size (1024 , 2048 , 4096):"), gbc);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1;
			insert.add(txtKey = new JTextField(20), gbc);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.EAST;
			insert.add(generateButton = new JButton("Generate"), gbc);
			generateButton.addActionListener(this);

			setLayout(new BorderLayout());
			add(insert);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equalsIgnoreCase("Generate")) {
				if (txtKey.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Key size is empty!!!", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					int size = Integer.parseInt(txtKey.getText());
					try {
						aSymmetric = new JavaASymmetric(algo, size);

						txtPrivate.setText(
								aSymmetric.convertBinary(aSymmetric.getGenerate().getPrivateKey().getEncoded()));
						txtPublic.setText(
								aSymmetric.convertBinary(aSymmetric.getGenerate().getPublicKey().getEncoded()));

					} catch (NoSuchAlgorithmException ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	public class GenerateKeyPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public GenerateKeyPanel() {
			JPanel detailsPane = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			detailsPane.add(privateKey = new JLabel("Private Key:"), gbc);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.weightx = 1;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			detailsPane.add(txtPrivate = new JTextField(40), gbc);

			gbc.gridx = 0;
			gbc.gridy += 2;
			gbc.anchor = GridBagConstraints.EAST;
			detailsPane.add(publicKey = new JLabel("Public Key:"), gbc);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.weightx = 1;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			detailsPane.add(txtPublic = new JTextField(40), gbc);

			gbc.gridx = 0;
			gbc.gridy += 2;
			gbc.anchor = GridBagConstraints.EAST;
			gbc.weightx = 1;
			detailsPane.add(secretKey = new JLabel("Secret key: "), gbc);

			gbc.gridx++;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.weightx = 1;
			detailsPane.add(txtSecretKey = new JTextField(40), gbc);

			setLayout(new BorderLayout());
			add(detailsPane);

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public static void main(String[] args) {
		new ASymmetricUI();
	}

}
