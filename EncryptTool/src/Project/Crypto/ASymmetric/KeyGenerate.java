package Project.Crypto.ASymmetric;

import java.security.*;
import java.util.Base64;

public class KeyGenerate {

    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public KeyGenerate(int keySize) throws NoSuchAlgorithmException {
        this.pair = keyPairGenerator(keySize);
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }


    public KeyPair keyPairGenerator(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        // Key size : 1024 or 2048 or 4096 bits
        generator.initialize(keySize , new SecureRandom());
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
