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
import java.io.ObjectOutputStream;

public class Sign {

    private KeyGenerate keyGenerate;

    public Sign() throws NoSuchAlgorithmException, NoSuchProviderException {
        this.keyGenerate = new KeyGenerate();
    }

//    public String hashFile(String file) throws NoSuchAlgorithmException, IOException {
//        MessageDigest md = MessageDigest.getInstance("SHA1");
//        File soucre = new File(file);
//        FileInputStream fis = new FileInputStream(soucre);
//        byte[] buffer = new byte[(int) soucre.length()];
//        int readBytes = 0;
//        while ((readBytes = fis.read(buffer)) != -1) {
//            md.update(buffer, 0, readBytes);
//        }
//
//        byte[] bytesData = md.digest();
//        return convertByteToHex(bytesData);
//    }
    
    public String convertByteToHex(byte[] data) {
        StringBuilder sb = new StringBuilder();

        for (byte datum : data) {
            sb.append(Integer.toString((datum & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public String checksum(DonHang obj) throws IOException, NoSuchAlgorithmException {
        if (obj == null) {
            return null;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
        }

        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(bos.toByteArray());

        byte[] hashing = m.digest();
        return convertByteToHex(hashing);
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

    public String signing(String input) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException,
            IOException, NoSuchProviderException {
        Signature signature = Signature.getInstance("DSA");
        signature.initSign(keyGenerate.getPrivateKey());
        signature.update(input.getBytes());
        byte[] bSignature = signature.sign();
        System.out.println("Đã ký thành công");
        return convertByteToHex(bSignature);
    }

    public boolean verifying(String sigture, String input) throws NoSuchAlgorithmException,
            InvalidKeyException,IOException, SignatureException, NoSuchProviderException {
        Signature signature = Signature.getInstance("DSA");
        signature.initVerify(keyGenerate.getPublicKey());
        byte[] messageBytes = input.getBytes();
        signature.update(messageBytes);
        byte[] recivedSignature = convertHexToByte(sigture);
        boolean isCorrect = signature.verify(recivedSignature);
        return isCorrect;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException,
            IOException, NoSuchProviderException {
        Sign s = new Sign();
        DonHang dh = new DonHang("232", "sfs", 3);
        String sig = s.signing(s.checksum(dh));
        System.out.println(s.checksum(dh));
        System.out.println(sig);
        System.out.println(s.verifying(sig, s.checksum(dh)));

    }

}
