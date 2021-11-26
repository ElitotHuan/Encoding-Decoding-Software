package Project.Crypto.ASymmetric;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class JavaHybrid {

    private JavaASymmetric asym;
    private String keySymmetric;
    private String algoKey;
    private String algorithm;
    private IvParameterSpec iv;


    public JavaHybrid(JavaASymmetric asym , int keySize , String algoKey , String  algorithm)  throws NoSuchAlgorithmException {
        this.algoKey = algoKey;
        this.asym = asym;
        this.keySymmetric = generateKey(keySize , algoKey);
        this.algorithm = algorithm;
        this.iv = generateIv();
    }

    public String generateKey(int length, String algorithm) throws
            NoSuchAlgorithmException {
        KeyGenerator generateKey = KeyGenerator.getInstance(algorithm);
        generateKey.init(length);
        SecretKey key = generateKey.generateKey();
        return getKeyStringForm(key);
    }

    public boolean checkALgo() {
        return algoKey.equalsIgnoreCase("AES");
    }

    public boolean checkMode() {return algorithm.contains("ECB");}


    public IvParameterSpec generateIv() {
        byte[] iv;

        if (checkALgo()){
            iv = new byte[16];
        } else {
            iv = new byte[8];
        }

        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }


    public String encryptUsingSymmetric(String message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        Cipher cipher = Cipher.getInstance(algorithm);
        SecretKey originalKey = getKeyOrginal(keySymmetric);

        if (checkMode()){
            cipher.init(Cipher.ENCRYPT_MODE, originalKey);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, originalKey , iv);
        }

        byte[] encryptedText = cipher.doFinal(message.getBytes());
        return base64Transform(encryptedText);
    }

    public void encryptFileUsingSymmetric(String input , String dest) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {

        File sourceFile = new File(input);
        File destFile = new File(dest);
        if (sourceFile.isFile()) {
            Cipher cipher = Cipher.getInstance(algorithm);
            SecretKey originalKey = getKeyOrginal(keySymmetric);
            if (checkMode()) {
                cipher.init(Cipher.ENCRYPT_MODE, originalKey);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, originalKey,iv);
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



    public void decryptFileUsingSymmetric(String soucre , String dest) throws NoSuchPaddingException, NoSuchAlgorithmException,
            IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {

        File sourceFile = new File(soucre);
        File destFile = new File(dest);
        if (sourceFile.isFile()) {
            Cipher cipher = Cipher.getInstance(algorithm);
            String decryptedKey = decryptSymmetricKey(encryptSymmetricKey());
            SecretKey originalKey = getKeyOrginal(decryptedKey);

            if (checkMode()) {
                cipher.init(Cipher.DECRYPT_MODE, originalKey);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, originalKey,iv);
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

    public String decryptUsingSymmetric(String encryptedMessage) throws IllegalBlockSizeException,
            BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {

        Cipher cipher = Cipher.getInstance(algorithm);
        String decryptedKey = decryptSymmetricKey(encryptSymmetricKey());
        SecretKey originalKey = getKeyOrginal(decryptedKey);

        if (checkMode()){
            cipher.init(Cipher.DECRYPT_MODE, originalKey);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, originalKey , iv);
        }

        byte[] base64Text = decodeEncrypted(encryptedMessage);
        byte[] original = cipher.doFinal(base64Text);
        return new String(original, StandardCharsets.UTF_8);

    }



    public String encryptSymmetricKey() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE , asym.getGenerate().getPublicKey());
        byte[] encodedText = cipher.doFinal(keySymmetric.getBytes());
        return base64Transform(encodedText);
    }


    public String decryptSymmetricKey(String encodedKey) throws InvalidKeyException, NoSuchPaddingException,
            NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE , asym.getGenerate().getPrivateKey());
        byte[] decode = decodeEncrypted(encodedKey);
        byte[] result = cipher.doFinal(decode);
        return new String(result , StandardCharsets.UTF_8);
    }


    public SecretKey getKeyOrginal(String origanlKey){
        byte[] decodedKey = Base64.getDecoder().decode(origanlKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, algoKey);
    }

    public String getKeyStringForm(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
    public static byte[] decodeEncrypted(String encrypted) {
        return Base64.getDecoder().decode(encrypted);
    }
    public String base64Transform(byte[] encoded) {
        return Base64.getEncoder().encodeToString(encoded);
    }

    public String getKeySymmetric(){return keySymmetric;}

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
//        JavaHybrid hybrid = new JavaHybrid( 128 , "AES" , "AES//PKCS5PADDING");
//
//        String keyAES = hybrid.keySymmetric;
//
//        String encryptMessage = hybrid.encryptUsingSymmetric("Huaan");
//        String encryptkey = hybrid.encryptSymmetricKey();
//        String decryptKey = hybrid.decryptSymmetricKey(encryptkey);
//        String decryptMessage = hybrid.decrypt(encryptMessage);
//
//
//        System.out.println("Original Key: " + keyAES);
//        System.out.println("Encrypt Message: " + encryptMessage);
//        System.out.println("Encrypt Key :" + encryptkey);
//        System.out.println("Decrypt Key : " + decryptKey);
//        System.out.println("Decrypt: " + decryptMessage);



    }

}
