package Project.Crypto.Signature;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashObject {
	public String getHashCode(Object obj) throws IOException, NoSuchAlgorithmException {
		if (obj == null) {
			return null;
		}

		// Object --> Mảng byte
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			oos.writeObject(obj);
		}

		MessageDigest m = MessageDigest.getInstance("SHA1");
		m.update(bos.toByteArray());

		byte[] hashing = m.digest();
		BigInteger number = new BigInteger(1, hashing);
		String hashtext = number.toString(16);
		return hashtext;
	}
}
