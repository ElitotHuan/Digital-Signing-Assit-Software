package Sign;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import Object.DonHang;

public class Sign {


	public String convertByteToHex(byte[] data) {
		StringBuilder sb = new StringBuilder();

		for (byte datum : data) {
			sb.append(Integer.toString((datum & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

//    public String checksum(DonHang obj) throws IOException, NoSuchAlgorithmException {
//        if (obj == null) {
//            return null;
//        }
//
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
//            oos.writeObject(obj);
//        }
//
//        MessageDigest m = MessageDigest.getInstance("SHA1");
//        m.update(bos.toByteArray());
//
//        byte[] hashing = m.digest();
//        return convertByteToHex(hashing);
//    }

	public byte[] convertHexToByte(String hex) {
		byte[] val = new byte[hex.length() / 2];
		for (int i = 0; i < val.length; i++) {
			int index = i * 2;
			int j = Integer.parseInt(hex.substring(index, index + 2), 16);
			val[i] = (byte) j;
		}
		return val;
	}

	public byte[] base64Decode(String s) {
		return Base64.getDecoder().decode(s);
	}

	public PrivateKey loadPrivateKey(String key64) throws GeneralSecurityException {
		byte[] clear = base64Decode(key64);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
		KeyFactory fact = KeyFactory.getInstance("DSA");
		PrivateKey priv = fact.generatePrivate(keySpec);
		Arrays.fill(clear, (byte) 0);
		return priv;
	}

	public String signing(String input, String privatekey) throws IOException, GeneralSecurityException {
		Signature signature = Signature.getInstance("DSA");
		signature.initSign(loadPrivateKey(privatekey));
		signature.update(input.getBytes());
		byte[] bSignature = signature.sign();
		System.out.println("Đã ký thành công");
		return convertByteToHex(bSignature);
	}

//    public boolean verifying(String sigture, String input , PublicKey pk) throws NoSuchAlgorithmException,
//            InvalidKeyException,IOException, SignatureException, NoSuchProviderException {
//        Signature signature = Signature.getInstance("DSA");
//        signature.initVerify(pk);
//        byte[] messageBytes = input.getBytes();
//        signature.update(messageBytes);
//        byte[] recivedSignature = convertHexToByte(sigture);
//        boolean isCorrect = signature.verify(recivedSignature);
//        return isCorrect;
//    }
	

}
