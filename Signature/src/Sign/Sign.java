package Sign;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.SignatureException;

import Object.DonHang;

public class Sign {

    private KeyGenerate keyGenerate;

    public Sign() throws NoSuchAlgorithmException, NoSuchProviderException {
        this.keyGenerate = new KeyGenerate();
    }

    public String hashFile(String file) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        File soucre = new File(file);
        FileInputStream fis = new FileInputStream(soucre);
        byte[] buffer = new byte[(int) soucre.length()];
        int readBytes = 0;
        while ((readBytes = fis.read(buffer)) != -1) {
            md.update(buffer, 0, readBytes);
        }

        byte[] bytesData = md.digest();
        return convertByteToHex(bytesData);
    }

    public String convertByteToHex(byte[] data) {
        StringBuilder sb = new StringBuilder();

        for (byte datum : data) {
            sb.append(Integer.toString((datum & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public byte[] convertHexToByte(String hex) {
        byte[] val = new byte[hex.length() / 2];
        for (int i = 0; i < val.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            val[i] = (byte) j;
        }
        return val;
    }

    public byte[] intToByteArray(int i) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeInt(i);
        dos.flush();
        return bos.toByteArray();
    }

    public String signing(int hashcode) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException,
            IOException, NoSuchProviderException {
        Signature signature = Signature.getInstance("DSA");
        signature.initSign(keyGenerate.getPrivateKey());
        signature.update(intToByteArray(hashcode));
        byte[] bSignature = signature.sign();
        System.out.println("Đã ký thành công");
        return convertByteToHex(bSignature);
    }

    public boolean verifying(String sigture, int hashCode) throws NoSuchAlgorithmException, InvalidKeyException,
            IOException, SignatureException, NoSuchProviderException {
        Signature signature = Signature.getInstance("SHA1withDSA");
        signature.initVerify(keyGenerate.getPublicKey());
        byte[] messageBytes = intToByteArray(hashCode);
        signature.update(messageBytes);
        byte[] recivedSignature = convertHexToByte(sigture);
        boolean isCorrect = signature.verify(recivedSignature);
        return isCorrect;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException,
            IOException, NoSuchProviderException {
        Sign s = new Sign();
        DonHang dh = new DonHang("232", "sfs", 3);
        String sig = s.signing(dh.hashCode());
        System.out.println(s.verifying(sig, dh.hashCode()));

    }

}
