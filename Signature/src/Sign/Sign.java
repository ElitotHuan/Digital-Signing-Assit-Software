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

//	public byte[] convertHexToByte(String hex) {
//		byte[] val = new byte[hex.length() / 2];
//		for (int i = 0; i < val.length; i++) {
//			int index = i * 2;
//			int j = Integer.parseInt(hex.substring(index, index + 2), 16);
//			val[i] = (byte) j;
//		}
//		return val;
//	}

	public PrivateKey loadPrivateKey(String key64) throws GeneralSecurityException {
		byte[] clear = Base64.getDecoder().decode(key64);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
		KeyFactory fact = KeyFactory.getInstance("DSA");
		PrivateKey priv = fact.generatePrivate(keySpec);
		Arrays.fill(clear, (byte) 0);
		return priv;
	}

//	public PublicKey loadPublicKey(String key64) throws GeneralSecurityException {
//		byte[] clear = Base64.getDecoder().decode(key64);
//		X509EncodedKeySpec  keySpec = new X509EncodedKeySpec(clear);
//		KeyFactory fact = KeyFactory.getInstance("DSA");
//		PublicKey priv = fact.generatePublic(keySpec);
//		Arrays.fill(clear, (byte) 0);
//		return priv;
//	}

	public String signing(String input, String privatekey) throws IOException, GeneralSecurityException {
		Signature signature = Signature.getInstance("DSA");
		signature.initSign(loadPrivateKey(privatekey));
		signature.update(input.getBytes());
		byte[] bSignature = signature.sign();
		System.out.println("Đã ký thành công");
		return convertByteToHex(bSignature);
	}

//	public boolean verifying(String sigture, String input, String pk) throws IOException, GeneralSecurityException {
//		Signature signature = Signature.getInstance("DSA");
//		signature.initVerify(loadPublicKey(pk));
//		byte[] messageBytes = input.getBytes();
//		signature.update(messageBytes);
//		byte[] recivedSignature = convertHexToByte(sigture);
//		boolean isCorrect = signature.verify(recivedSignature);
//		return isCorrect;
//	}

//	public static void main(String[] args) throws IOException, GeneralSecurityException {
//		Sign s = new Sign();
//		DonHang dh = new DonHang("sfs", "A", 3);
//		System.out.println(s.checksum(dh));
//		String signa = s.signing(s.checksum(dh),
//				"MIIBTAIBADCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoEFwIVAIHECgmxQH+xobiJxmLmEiWzzqV1");
//		System.out.println(signa);
//		System.err.println(s.verifying(signa, s.checksum(dh), "MIIBtzCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoDgYQAAoGAJsNAGi+YsP0CH2Xgb9r+7ao+Gn1S/YWI/TZFRf6y8BqBmkAqR5gjYrmttk6vSxv1zJW0P1I/cpGst2ZGiUY9lYeBEvqFPxDZ7/wG4AEQfPW6LXs4W4xYuih5pWympEjlN4vZKoU7DE/3VcvrOxCC2OmgFMoyjDmH6NoPbmy+fi8="));
//	}
	
//	public static void main(String[] args) throws IOException, GeneralSecurityException {
//		Sign s = new Sign();
//		PrivateKey pk = s.loadPrivateKey("MIIBSwIBADCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoEFgIUBYjYHEtXfaSsRBoZfmc8hOGwatw=");
//		System.out.println(s.verifying("302c02143ed0e01b3e1c12c3db341bf354b8073f81f8bfb402144eb0b3d26cb4658037d05cb89062aad35ec9925d","d4w", "MIIBtzCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoDgYQAAoGAN/JrWZOx2jPf97BL5Cs2jL5NiVaiSJijv3aiGnHCRQzV04+xzJzjwnVxRh6ceGgQqN3kX0/aCJr3Sj2DdOu1GTIpIQoBU6c1GpJohmy0NFvfcrSsBf8VYqIum5VGHUe0sMIgYX86wZ4NVlnbtg7uYb17jTVGxX1t8MxbV8MAOP8="));
//	}

}
