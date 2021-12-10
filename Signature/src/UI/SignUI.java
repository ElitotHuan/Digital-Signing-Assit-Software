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

public class SignUI extends JFrame {

    private Sign s;
    private JTextField txtHash;
    private JTextArea signa;

    public SignUI() {
        setTitle("Ký");
        setLayout(new BorderLayout());
        Container pane = getContentPane();

        JPanel p1 = new JPanel();

        p1.add(new Content());

        pane.add(p1);

        pack();
        setVisible(true);
        setSize(680, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public class Content extends JPanel implements ActionListener {

        public Content() {
            JPanel res = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.EAST;
            res.add(new JLabel("Nhập mã hash: "), gbc);

            gbc.gridx++;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.WEST;
            txtHash = new JTextField(40);
            res.add(txtHash, gbc);

            gbc.gridx++;
            JButton buttonSign = new JButton("Ký");
            res.add(buttonSign, gbc);
            buttonSign.addActionListener(this);

            gbc.gridx = 1;
            gbc.gridy += 5;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weighty = 1;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            signa = new JTextArea(20, 0);
            signa.setLineWrap(true);
            signa.setWrapStyleWord(true);
            res.add(signa, gbc);

            setLayout(new BorderLayout());
            add(res);

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
                        int hasCode = Integer.parseInt(txtHash.getText());
                        signa.setText(s.signing(hasCode));
                        JOptionPane.showMessageDialog(null, "Bạn đã ký Thành công", "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
                    } catch (NoSuchAlgorithmException | InvalidKeyException
                            | SignatureException | NoSuchProviderException | IOException e1) {
                        // TODO Auto-generated catch block
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(null, "Please insert number", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SignUI signUI = new SignUI();
    }
    
    

}
