package Project.Crypto.Signature;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class KeyGenerate {
	private String algorithm;
	private int keySize;

	public KeyGenerate(String algorithm, int keySize) {
		this.algorithm = algorithm;
		this.keySize = keySize;
	}

	public boolean generate(String srcPri, String srcPub) {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(this.algorithm);
			keyPairGenerator.initialize(this.keySize);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();

			String keyPri64 = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
			String keyPub64 = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

			process(srcPub, keyPub64);
			process(srcPri, keyPri64);

			return true;
		} catch (Exception exception) {
			System.out.println("Error in Key generate: " + exception.getMessage());
		}
		return false;

	}

	private void process(String src, String content) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(src))));
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			System.out.println("Error in Key process: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public PublicKey readPublic(String src) {
		try {
			String key64 = new String(Files.readAllBytes(Paths.get(src)));
			byte[] keybytes = Base64.getDecoder().decode((key64.getBytes("UTF-8")));
			X509EncodedKeySpec xeks = new X509EncodedKeySpec(keybytes);
			KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
			return keyFactory.generatePublic(xeks);

		} catch (Exception exception) {
			System.out.println("Asymmetric readPublic: " + exception.getMessage());
			throw new RuntimeException(exception);
		}
	}

	public PrivateKey readPrivate(String src) {
		try {
			String key64 = new String(Files.readAllBytes(Paths.get(src)));
			byte[] keyByte = Base64.getDecoder().decode((key64.getBytes("UTF-8")));
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyByte);
			KeyFactory keyFactory = KeyFactory.getInstance(this.algorithm);
			Arrays.fill(keyByte, (byte) 0);
			return keyFactory.generatePrivate(keySpec);
		} catch (Exception exception) {
			System.out.println("Asymmetric readPrivate: " + exception.getMessage());
			throw new RuntimeException(exception);
		}
	}

// DSA key size from 512 - 3072
// RSA key size from 512 - 4096
	public static void main(String[] args) {
		KeyGenerate k = new KeyGenerate("DSA", 1024);
		k.generate("./priv.txt", "./pub.txt");

		System.out.println(Base64.getEncoder().encodeToString(k.readPrivate("./priv.txt").getEncoded()));
		System.out.println(Base64.getEncoder().encodeToString(k.readPublic("./pub.txt").getEncoded()));
	}

}
