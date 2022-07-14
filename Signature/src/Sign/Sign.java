package Sign;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class Sign {

	public String convertByteToHex(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (byte datum : data) {
			sb.append(Integer.toString((datum & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	public PrivateKey loadPrivateKey(String key64) throws GeneralSecurityException, UnsupportedEncodingException {
		byte[] clear = Base64.getDecoder().decode((key64.getBytes("UTF-8")));
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

}
