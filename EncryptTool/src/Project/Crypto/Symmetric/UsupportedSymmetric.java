package Project.Crypto.Symmetric;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;

public class UsupportedSymmetric
{
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        // generate key
        Security.addProvider(new BouncyCastleProvider());
        KeyGenerator keyGen = KeyGenerator.getInstance("Serpent");
        SecretKey secretKey = keyGen.generateKey();
        // get Cipher and init it for encryption
        Cipher cipher = Cipher.getInstance("Serpent/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        // encrypt data
        byte[] cipherText = cipher.doFinal("data".getBytes());
        // get the initialization vector from the cipher
        byte[] ivBytes = cipher.getIV();
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        // raw key material (usually the key will be securely stored/transmitted)
        byte[] keyBytes = secretKey.getEncoded();
        // create a SecretKeySpec from key material
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "Serpent");
        // get Cipher and init it for encryption
        cipher = Cipher.getInstance("Serpent/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
        byte[] plainText = cipher.doFinal(cipherText);


        System.out.println(Arrays.toString(cipherText));
        System.out.println(new String(plainText , StandardCharsets.UTF_8));

    }
}
