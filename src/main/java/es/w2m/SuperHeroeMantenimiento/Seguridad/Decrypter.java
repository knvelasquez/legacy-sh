package es.w2m.SuperHeroeMantenimiento.Seguridad;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Decrypter {
	Cipher dcipher;

    byte[] salt = new String("12345678").getBytes();
    int iterationCount = 1024;
    int keyStrength = 128;
    SecretKey key;
    byte[] iv;

    public Decrypter(String passPhrase) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount, keyStrength);
        SecretKey tmp = factory.generateSecret(spec);
        key = new SecretKeySpec(tmp.getEncoded(), "AES");
        dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    }

    public String encrypt(String data) throws Exception {
        dcipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters params = dcipher.getParameters();
        iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] utf8EncryptedData = dcipher.doFinal(data.getBytes());
        String base64EncryptedData = new sun.misc.BASE64Encoder().encodeBuffer(utf8EncryptedData);
        return base64EncryptedData;
    }

    public String decrypt(String base64EncryptedData) throws Exception {
        dcipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] decryptedData = new sun.misc.BASE64Decoder().decodeBuffer(base64EncryptedData);
        byte[] utf8 = dcipher.doFinal(decryptedData);
        return new String(utf8, "UTF8");
    }

    
	public static byte[] decodeHexString(String hexString) {
		if (hexString.length() % 2 == 1) {
			throw new IllegalArgumentException(
					"Invalid hexadecimal String supplied.");
	        }
	
	        byte[] bytes = new byte[hexString.length() / 2];
	        for (int i = 0; i < hexString.length(); i += 2) {
	            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
	        }  
	        return bytes;
	 }
	
	 public static byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }
	 private static int toDigit(char hexChar) {
	        int digit = Character.digit(hexChar, 16);
	        if (digit == -1) {
	            throw new IllegalArgumentException(
	                "Invalid Hexadecimal Character: "+ hexChar);
	    }
		return digit;
	}
    public static void main(String args[]) {
    	Charset UTF_8 = StandardCharsets.UTF_8;
		String result;
		String OUTPUT_FORMAT = "%-30s:%s";
        String PASSWORD = "plnlrtfpijpuhqylxbgqiiyipieyxvfsavzgxbbcfusqkozwpngsyejqlmjsytrmd";

        // plain text
        String pText = "AES-GSM Password-Bases encryption!";

        // convert hex string to byte[]
        byte[] salt = decodeHexString("A009C1A485912C6AE630D3E744240B04");

        
		try {
			//result=CriptografiaPBKDF2.cifrar("valor");
			//result=CriptografiaPBKDF2.decrypt(result,PASSWORD);
			
			Decrypter decrypter = new Decrypter("ABCDEFGHIJKL");
	        String encrypted = decrypter.encrypt("the quick brown fox jumps over the lazy dog");
	        result=encrypted;
	        System.out.println(encrypted);
	        String decrypted = decrypter.decrypt(encrypted);
	        System.out.println(decrypted);
	        result=decrypted;
			
		} catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
			result=exception.getMessage();
		} catch (Exception exception) {
			result=exception.getMessage();
		}	
    }
    
}
