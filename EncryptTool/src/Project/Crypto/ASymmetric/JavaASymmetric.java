package Project.Crypto.ASymmetric;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class JavaASymmetric {

    private KeyGenerate generate;
    private String algorithm;
    private int keySize;

    public JavaASymmetric(String algorithm ,int keySize) throws NoSuchAlgorithmException {
        this.keySize = keySize;
        this.generate = new KeyGenerate(keySize);
        this.algorithm = algorithm;
    }

    public String encrypt(String input) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE , generate.getPublicKey());
        byte[] encodedText = cipher.doFinal(input.getBytes());
        return convertBinary(encodedText);
    }

    public String decrypt(String input) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE , generate.getPrivateKey());
        byte[] decode = decodeEncrypted(input);
        byte[] result = cipher.doFinal(decode);
        return new String(result , StandardCharsets.UTF_8);
    }

    public void encryptFile(String soucre , String destination) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        File soucreFile = new File(soucre);
        File destFile = new File(destination);

        FileInputStream fis = new FileInputStream(soucreFile);
        FileOutputStream fos = new FileOutputStream(destFile);

        if (soucreFile.isFile()){
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE , generate.getPrivateKey());

            byte[] buffer = new byte[(int) soucre.length()];

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
        }
    }

    public void decryptFile(String soucre , String destination) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        File soucreFile = new File(soucre);
        File destFile = new File(destination);

        FileInputStream fis = new FileInputStream(soucreFile);
        FileOutputStream fos = new FileOutputStream(destFile);

        if (soucreFile.isFile()){
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, generate.getPublicKey());

            byte[] buffer = new byte[(int) soucre.length()];

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
        }
    }


    //Binary --> String
    public String convertBinary(byte[] key){
        return Base64.getEncoder().encodeToString(key);
    }

    public static byte[] decodeEncrypted(String encrypted) {
        return Base64.getDecoder().decode(encrypted);
    }

    public KeyGenerate getGenerate() {
        return generate;
    }




    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        JavaASymmetric a = new JavaASymmetric("RSA",1024);
        String encrypt = a.encrypt("VO Doan Minh Huan");

        System.out.println("Encrypt: " + encrypt);
        System.out.println("Decrypt: " + a.decrypt(encrypt));
    }






}
