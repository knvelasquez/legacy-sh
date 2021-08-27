/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Seguridad;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

/**
 * @author Kevin Velásquez
 *
 */
@Service
public class CipherSecurity {

	private static final String ENCRYPT_ALGORITMO = "AES/CBC/PKCS5Padding";// ChaCha20-Poly1305
	private static final int NONCE_LEN = (16 + 32); // 96 bits, 12 bytes

	private String bytesToHexadecimal(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte temp : bytes) {
			result.append(String.format("%02x", temp));
		}
		return result.toString();
	}

	public byte[] stringToByte(String value) {
		int len = value.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(value.charAt(i), 16) << 4)
					+ Character.digit(value.charAt(i + 1), 16));
		}	
		return data;		
	}

	private byte[] getSalt(int value) {
		byte[] newNonce = new byte[value];
		new SecureRandom().nextBytes(newNonce);
		return newNonce;
	}

	private byte[] getSalt() {
		byte[] newNonce = new byte[16];
		new SecureRandom().nextBytes(newNonce);
		return newNonce;
	}

	public String decrypt(String key, String value) {
		try {
			byte[] valueByte = this.stringToByte(value);
			byte[] keyByte = this.stringToByte(key);			
			SecretKey originalKey = new SecretKeySpec(keyByte, 0, keyByte.length, "AES");// "ChaCha20"
			//
			ByteBuffer byteBuffer = ByteBuffer.wrap(valueByte);

			// split cText to get the appended nonce
			byte[] textEncrypted = new byte[valueByte.length - NONCE_LEN];
			byte[] salt = new byte[16];
			byte[] nonce = new byte[32];

			byteBuffer.get(textEncrypted);
			byteBuffer.get(salt);
			byteBuffer.get(nonce);

			Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITMO);
			cipher.init(Cipher.DECRYPT_MODE, originalKey, new IvParameterSpec(salt));

			// decrypted text
			return new String(cipher.doFinal(textEncrypted));

		} 
		catch(StringIndexOutOfBoundsException exception) {
			exception.printStackTrace();
			return null;
		}
		catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
				| BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	public CipherModel encrypt(String value) {
		return this.encrypt(value, this.getKey());
	}

	public CipherModel encrypt(String value, String key) {
		return this.encrypt(value, this.getKey(key));
	}

	public CipherModel encrypt(String value, SecretKey key) {
		try {
			Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITMO);
			byte[] salt = getSalt();
			byte[] nonce = getSalt(32);
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(salt));
			byte[] textEncrypted = cipher.doFinal(value.getBytes());
			// send the ouput
			return new CipherModel(this.bytesToHexadecimal(key.getEncoded()),
					this.bytesToHexadecimal(ByteBuffer.allocate(textEncrypted.length + salt.length + nonce.length)
							.put(textEncrypted).put(salt).put(nonce).array()));

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException exception) {
			exception.printStackTrace();
			return new CipherModel("", "Error encryptando el texto, favor revisar el texto y clave indicada");
		}
	}

	public SecretKey getKey() {
		return this.getKey("uso-de-clave-statica-123");
	}

	public SecretKey getKey(String phrase) {
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			PBEKeySpec keySpec = new PBEKeySpec(phrase.toCharArray(), this.getSalt(), 2048, 128);
			SecretKey secret = factory.generateSecret(keySpec);
			return new SecretKeySpec(secret.getEncoded(), "AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}
}
