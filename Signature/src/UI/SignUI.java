package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import javax.swing.*;

import Sign.Sign;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class SignUI extends JFrame {

    private Sign s;
    private JTextField txtHash;
    private JTextArea signa;

    public SignUI() {
        setTitle("Ký");
        setLayout(new BorderLayout());

        add(new MainFrame());

        pack();
        setVisible(true);
        setSize(700, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public class MainFrame extends JPanel {

        public MainFrame() {
            setBorder(new EmptyBorder(8, 8, 8, 8));
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1;

            Content content = new Content();
            add(content, gbc);
        }

    }

    public class Content extends JPanel implements ActionListener {

        public Content() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            add(new JLabel("Nhập mã hash: "), gbc);

            gbc.gridx++;
            gbc.weightx = 0.5;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.WEST;
            txtHash = new JTextField(40);
            add(txtHash, gbc);

            gbc.gridx += 2;
            JButton buttonSign = new JButton("Ký");
            add(buttonSign, gbc);
            buttonSign.addActionListener(this);

            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridy += 5;
            gbc.gridx = 1;
            signa = new JTextArea(20 , 10);
            signa.setLineWrap(true);
            signa.setWrapStyleWord(true);
            add(signa, gbc);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (e.getActionCommand().equalsIgnoreCase("Ký")) {
                if (txtHash.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Xin hãy nhập chữ ký", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        s = new Sign();

                        signa.setText(s.signing(txtHash.getText()));
                        JOptionPane.showMessageDialog(null, "Bạn đã ký Thành công", "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (NoSuchAlgorithmException | InvalidKeyException
                            | SignatureException | NoSuchProviderException | IOException e1) {
                        // TODO Auto-generated catch block
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SignUI signUI = new SignUI();
    }

}
