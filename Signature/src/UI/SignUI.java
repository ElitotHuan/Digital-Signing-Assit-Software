package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import javax.swing.*;
import Sign.Sign;
import javax.swing.border.EmptyBorder;

public class SignUI extends JFrame {

	private Sign s;
	private JTextField txtHash;
	private JTextArea signa, privateKey;
	private JScrollPane pkPane;
	private int top = 3, left = 3, bottom = 3, right = 3;
	private Insets i = new Insets(top, left, bottom, right);

	public SignUI() {
		EventQueue.invokeLater(new Runnable() {

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

				setTitle("Ký");
				setLayout(new BorderLayout());
				add(new MainFrame());
				pack();
				setVisible(true);
				setLocationRelativeTo(null);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
			}

		});

	}

	public class MainFrame extends JPanel {

		public MainFrame() {
			setBorder(new EmptyBorder(10, 10, 10, 10));
			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;

			Content content = new Content();
			add(content, gbc);
		}

	}

	public class Content extends JPanel implements ActionListener {

		public Content() {

			setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			
			gbc.insets = i;
			gbc.weightx=0.5;
			gbc.weighty=0.5;
		        
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.anchor = GridBagConstraints.EAST;
			add(new JLabel("Nhập mã hash: "), gbc);

			gbc.gridx++;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.anchor = GridBagConstraints.WEST;
			txtHash = new JTextField(40);
			add(txtHash, gbc);

			gbc.gridx = 0;
			gbc.gridy += 1;
			gbc.anchor = GridBagConstraints.EAST;
			add(new JLabel("Nhập khóa riêng: "), gbc);

			gbc.gridx++;
			gbc.fill = GridBagConstraints.BOTH;
			gbc.anchor = GridBagConstraints.WEST;
			privateKey = new JTextArea(10, 10);
			privateKey.setLineWrap(true);
			privateKey.setWrapStyleWord(true);
			pkPane = new JScrollPane(privateKey);
			add(pkPane, gbc);

			gbc.gridy += 1;
			gbc.gridx = 1;
			JButton buttonSign = new JButton("Ký");
			add(buttonSign, gbc);
			buttonSign.addActionListener(this);

			gbc.gridy++;
			gbc.gridx = 0;
			gbc.anchor = GridBagConstraints.EAST;
			add(new JLabel("Chữ ký: "), gbc);

			gbc.fill = GridBagConstraints.BOTH;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridx++;
			signa = new JTextArea(10, 10);
			signa.setLineWrap(true);
			signa.setWrapStyleWord(true);
			pkPane = new JScrollPane(signa);
			add(pkPane, gbc);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getActionCommand().equalsIgnoreCase("Ký")) {
				if (txtHash.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Xin hãy nhập mã hash", "Lỗi", JOptionPane.ERROR_MESSAGE);
				} else if (privateKey.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Xin hãy nhập khóa được cung cấp", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						s = new Sign();
						String signature = s.signing(txtHash.getText(), privateKey.getText());
						signa.setText(signature);
						JOptionPane.showMessageDialog(null, "Bạn đã ký Thành công", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException
							| NoSuchProviderException | IOException e1) {
						// TODO Auto-generated catch block
					} catch (InvalidKeySpecException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (GeneralSecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
		SignUI s = new SignUI();
	}

}
