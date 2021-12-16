package Sign;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public final class KeyGenerate {

    private final KeyPair pair;
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public KeyGenerate() throws NoSuchAlgorithmException, NoSuchProviderException {
        this.pair = keyPairGenerator(1024);
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public KeyPair keyPairGenerator(int keySize) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("DSA");
        generator.initialize(keySize);
        return generator.generateKeyPair();
    }

    public String convertBinary(byte[] key) {
        return Base64.getEncoder().encodeToString(key);
    }
    
    public byte[] base64Decode(String s) {
        return Base64.getDecoder().decode(s);
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public  PrivateKey loadPrivateKey(String key64) throws GeneralSecurityException {
        byte[] clear = base64Decode(key64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
        KeyFactory fact = KeyFactory.getInstance("DSA");
        PrivateKey priv = fact.generatePrivate(keySpec);
        Arrays.fill(clear, (byte) 0);
        return priv;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, GeneralSecurityException {
        KeyGenerate kg = new KeyGenerate();
        System.out.println(kg.convertBinary(kg.getPrivateKey().getEncoded()));
        PrivateKey pk = kg.loadPrivateKey("MIIBSwIBADCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoEFgIUFRwK6Oz1Iu7m2IM1HSkqNJWLITw=");
        System.out.println(kg.convertBinary(pk.getEncoded()));
    }

}
