/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Seguridad;

import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author Kevin Velasquez
 * 
 */
public class ChaCha20Poly1305 {
	//private static final String ENCRYPT_ALGORITMO = "ChaCha20-Poly1305";
	private static final String ENCRYPT_ALGORITMO = "AES/CBC/PKCS5Padding";
	private static final int NONCE_LEN = 12; // 96 bits, 12 bytes

	private byte[]iv2;
	// if no nonce, generate a random 12 bytes nonce
	public byte[] encrypt(byte[] pText, SecretKey key) throws Exception {
		return encrypt(pText, key, getNonce());
	}

	public byte[] encrypt(byte[] pText, SecretKey key, byte[] nonce) throws Exception {
		Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITMO);
		// IV, initialization value with nonce
		byte[]salt = new String("1234567890123456").getBytes();
		
		//IvParameterSpec iv = new IvParameterSpec(nonce);	
		IvParameterSpec iv = new IvParameterSpec(salt);	
		
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		//cipher.init(Cipher.ENCRYPT_MODE, key);
		
		
		//AlgorithmParameters params = cipher.getParameters();
        //this.iv2 = params.getParameterSpec(IvParameterSpec.class).getIV();
        
		byte[] encryptedText = cipher.doFinal(pText);

		// append nonce to the encrypted text
		//byte[] nonce2 = new byte[1024];
		//new SecureRandom().nextBytes(nonce2);

		byte[] output = ByteBuffer.allocate(encryptedText.length +16/*+ NONCE_LEN + nonce2.length*/).put(encryptedText)
				.put(salt)
				/*.put(nonce).put(nonce2)*/.array();

		return output;
	}

	public byte[] decrypt(byte[] cText, SecretKey key) throws Exception {

		ByteBuffer bb = ByteBuffer.wrap(cText);
		ByteBuffer bb2 = ByteBuffer.wrap(cText);

		// split cText to get the appended nonce
		byte[] encryptedText = new byte[cText.length -16 /*- NONCE_LEN - 1024*/];
		byte[] encryptedText2 = new byte[cText.length /*- NONCE_LEN - 1024*/];
		byte[] nonce = new byte[16];
		//byte[] nonce2 = new byte[1024];
			
		bb2.get(encryptedText2);
		bb.get(encryptedText);
		bb.get(nonce);
		//bb.get(nonce2);

		Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITMO);

		AlgorithmParameters params = cipher.getParameters();
		byte[]iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		
		byte[]salt = new String("1234567890123456").getBytes();
		IvParameterSpec iv3= new IvParameterSpec(nonce);
		//IvParameterSpec iv3= new IvParameterSpec(salt);
				
		//cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(iv));	
		cipher.init(Cipher.DECRYPT_MODE, key,iv3);	
		//cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(this.iv2));//FUNCIONA

		
		// decrypted text
		byte[] output = cipher.doFinal(encryptedText);

		return output;

	}

	// 96-bit nonce (12 bytes)
	private static byte[] getNonce() {
		byte[] newNonce = new byte[16];
		new SecureRandom().nextBytes(newNonce);
		return newNonce;
	}
}
