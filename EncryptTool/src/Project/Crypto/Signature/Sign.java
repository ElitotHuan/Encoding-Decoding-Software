package Project.Crypto.Signature;

import Project.Crypto.ASymmetric.KeyGenerate;
import Project.Crypto.Hash.Hash;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

public class Sign {
    private KeyGenerate keyPair;
    private Hash h;

    public Sign(int keysize) throws NoSuchAlgorithmException {
        this.keyPair = new KeyGenerate(keysize);
        this.h = new Hash("MD5");
    }

    public String Sign_NguoiGoi(String input) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(keyPair.getPrivateKey());
        signature.update(input.getBytes());
        byte[] bSignature = signature.sign();
        System.out.println("Đã ký thành công");
        return Base64.getEncoder().encodeToString(bSignature);
    }

    public boolean Verify_Sign(String input,String sign) throws NoSuchAlgorithmException,
            InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(keyPair.getPublicKey());
        signature.update(input.getBytes());
        byte[] originalSign = Base64.getDecoder().decode(sign);
        return signature.verify(originalSign);
    }



    public static void main(String[] args) throws NoSuchAlgorithmException
            , SignatureException, InvalidKeyException, IOException {
        Sign s = new Sign(1024);
        String hashStr = s.h.hashFile("/home/eliothuan/Music/Captains of the Sky- Sky Sailing.mp3");
        System.out.println(s.Sign_NguoiGoi(hashStr));
        System.out.println(s.Verify_Sign(hashStr,s.Sign_NguoiGoi(hashStr)));

    }


}
