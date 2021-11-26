package Project.Crypto.Hash;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    private String algorithm;

    public Hash(String algorithm){
        this.algorithm = algorithm;
    }

    public String hashString(String input) throws NoSuchAlgorithmException {
        MessageDigest md =  MessageDigest.getInstance(algorithm);
        md.update(input.getBytes());
        byte[] hashedData = md.digest();
        return convertByteToHex(hashedData);
    }

    public String hashFile(String filePath) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        File soucre = new File(filePath);
        FileInputStream fis = new FileInputStream(soucre);
        byte[] buffer = new byte[(int) soucre.length()];
        int readBytes = 0;
        while ((readBytes = fis.read(buffer)) != -1){
            md.update(buffer , 0 , readBytes);
        }

        byte[] bytesData = md.digest();
        return convertByteToHex(bytesData);
    }

    public static String convertByteToHex(byte[] data) {
        StringBuilder sb = new StringBuilder();

        for (byte datum : data) {
            sb.append(Integer.toString((datum & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }



    public static void main(String[] args) throws NoSuchAlgorithmException {
        Hash h = new Hash("SHA-1");
        System.out.println(h.hashString("Huân"));

    }




}
