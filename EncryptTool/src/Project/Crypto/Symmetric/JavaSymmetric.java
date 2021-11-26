package Project.Crypto.Symmetric;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class JavaSymmetric {

    private final String algorithm;
    private final SecretKey key;
    private final IvParameterSpec iv;
    private final String algokey;

    public JavaSymmetric(String algorithm, String algoKey, int keySize)
            throws NoSuchAlgorithmException, NoSuchProviderException {
        Security.addProvider(new BouncyCastleProvider());
        this.algorithm = algorithm;
        this.algokey = algoKey;
        this.key = generateKey(keySize, algoKey);
        this.iv = generateIv();
    }

    public SecretKey generateKey(int length, String algorithm) throws
            NoSuchAlgorithmException, NoSuchProviderException {

        KeyGenerator generateKey = KeyGenerator.getInstance(algorithm);
        generateKey.init(length);
        return generateKey.generateKey();
    }

    public boolean checkALgo() {
        return algokey.equalsIgnoreCase("AES") || algokey.equalsIgnoreCase("Twofish")
                ||  algokey.equalsIgnoreCase("Serpent");
    }

    public IvParameterSpec generateIv() {
        byte[] iv;

        if (checkALgo()) {
            iv = new byte[16];
        } else {
            iv = new byte[8];
        }

        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }


    // Đưa key về dạng base64
    public String getKeyStringForm(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static String base64Transform(byte[] encoded) {
        return Base64.getEncoder().encodeToString(encoded);
    }

    public static byte[] decodeEncrypted(String encrypted) {
        return Base64.getDecoder().decode(encrypted);
    }

    //byte[] --> String
    public String StringUTFtransform(byte[] b) {
        return new String(b, StandardCharsets.UTF_8);
    }

    public boolean checkMode() {
        return algorithm.contains("CBC") || algorithm.contains("PCBC")
                || algorithm.contains("CFB") || algorithm.contains("OFB") || algorithm.contains("CTR");
    }


    public String encrypt(String input) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchProviderException {
        Cipher cipher = Cipher.getInstance(algorithm);
        if (checkMode()) {
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        }
        byte[] encryptedText = cipher.doFinal(input.getBytes());
        return base64Transform(encryptedText);
    }

    public String decrypt(String encryptedMessage) throws UnsupportedEncodingException, IllegalBlockSizeException,
            BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {

        Cipher cipher = Cipher.getInstance(algorithm);
        if (checkMode()) {
            cipher.init(Cipher.DECRYPT_MODE, this.key, iv);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, this.key);
        }
        byte[] base64Text = decodeEncrypted(encryptedMessage);
        byte[] original = cipher.doFinal(base64Text);
        return StringUTFtransform(original);
    }

    public void encryptFile(String input, String destination) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        File sourceFile = new File(input);
        File destFile = new File(destination);
        if (sourceFile.isFile()) {
            Cipher cipher = Cipher.getInstance(algorithm);
            if (checkMode()) {
                cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, key);
            }
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] buffer = new byte[(int) sourceFile.length()];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    fos.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();

            if (outputBytes != null) {
                fos.write(outputBytes);
            }
            fis.close();
            fos.close();

        } else {
            System.out.println("Please insert file");
        }


    }

    public void decryptFile(String source, String destination) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IOException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        File sourceFile = new File(source);
        File destFile = new File(destination);
        if (sourceFile.isFile()) {
            Cipher cipher = Cipher.getInstance(algorithm);
            if (checkMode()) {
                cipher.init(Cipher.DECRYPT_MODE, key, iv);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, key);
            }

            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(destFile);

            byte[] buffer = new byte[(int) sourceFile.length()];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    fos.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                fos.write(outputBytes);

            }
            fis.close();
            fos.close();

        } else {
            System.out.println("Please insert file");
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, BadPaddingException, InvalidKeyException, NoSuchProviderException {
        //VUx/8a4==
        JavaSymmetric jav = new JavaSymmetric("AES/NoMode/NoPadding", "AES", 128);
        System.out.println(jav.encrypt("Huân"));
        System.out.println(jav.decrypt(jav.encrypt("Huân")));
    }

    public SecretKey getKey() {
        return key;
    }

}
