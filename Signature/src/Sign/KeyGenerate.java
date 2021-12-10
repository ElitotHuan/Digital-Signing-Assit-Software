package Sign;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class KeyGenerate {

    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;

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

    public String convertBinary(byte[] key){
        return Base64.getEncoder().encodeToString(key);
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
    
    
}