/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Seguridad;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Kevin Velásquez
 *
 */
public class CriptografiaPBKDF2 {

	private static int ITERACCIONES = 1000;
	private static int LONGITUDCLAVE = 512;
	// Consultar sección SecretKeyFactory en la Documentacion de nombres de
	// algoritmos estándar de la arquitectura de criptografía Java
	// para obtener información sobre los nombres de algoritmos estándar
	private static String ALGORITMOCLAVESECRETA = "PBKDF2WithHmacSHA1";

	/**
	 * Metodo para cifrar una cadena de caracteres
	 * con el algoritmo PBKDF2
	 * */
	public static String cifrar(String valor) throws NoSuchAlgorithmException, InvalidKeySpecException{
		char[] valorEnCharacteres = valor.toCharArray();		
		byte[] salt;
		// Obtiene valor aleatorio generado por el sistema se agrega junto con el valor cifrado o hash
		salt = ValorAleatorioGeneradoPorElSistema();
		PBEKeySpec cifradoDeClavePBE = new PBEKeySpec(valorEnCharacteres, salt, ITERACCIONES, LONGITUDCLAVE);
		// Nombre Estandar del algoritmo de clave secreta solicitado
		SecretKeyFactory fabricaClaveSecreta = SecretKeyFactory.getInstance(ALGORITMOCLAVESECRETA);
		byte[] cifradoEnByte = fabricaClaveSecreta.generateSecret(cifradoDeClavePBE).getEncoded();		
		return convertirHexadecimal(cifradoEnByte);
	}

	/**
	 * Retorna un valor aleatorio generado por el sistema se agrega junto con el
	 * valor cifrado o hash Evita duplicidad en caso que dos usuarios usen la misma
	 * contrasenia se usa para validar al usuario en su proximo inicio de sesion
	 * 
	 */
	private static byte[] ValorAleatorioGeneradoPorElSistema() throws NoSuchAlgorithmException {
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		secureRandom.nextBytes(new byte[16]);
		return salt;
	}

	/**
	 * Metodo para convertir un byte en hexadecimal
	 * 
	 * */
	private static String convertirHexadecimal(byte[] array) throws NoSuchAlgorithmException {
		BigInteger GranEntero = new BigInteger(1, array);
		String hexadecimal = GranEntero.toString(16);
		int paddingLength = (array.length * 2) - hexadecimal.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hexadecimal;
		} else {
			return hexadecimal;
		}
	}
	
	private static final Charset UTF_8 = StandardCharsets.UTF_8;
	private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";

    private static final int TAG_LENGTH_BIT = 64; // must be one of {128, 120,     112, 104, 96}
    private static final int IV_LENGTH_BYTE = 12;
    private static final int SALT_LENGTH_BYTE = 16;
    public static final int ITERATION_COUNT = 1000;
    public static final int KEY_LENGTH = 512;
	
	// we need the same password, salt and iv to decrypt it
    public static String decrypt(String cText, String password) throws Exception {
    	password=ALGORITMOCLAVESECRETA;
        byte[] decode = Base64.getDecoder().decode(cText.getBytes(UTF_8));

        // get back the iv and salt from the cipher text
        ByteBuffer bb = ByteBuffer.wrap(decode);

        byte[] iv = new byte[IV_LENGTH_BYTE];
        bb.get(iv);

        byte[] salt = new byte[SALT_LENGTH_BYTE];
        bb.get(salt);

        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);

        salt=ValorAleatorioGeneradoPorElSistema();
        // get back the aes key from the same password and salt
        SecretKey aesKeyFromPassword = getAESKeyFromPassword(password.toCharArray(), salt);

        //Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        //cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new IvParameterSpec(iv));

        byte[] plainText = cipher.doFinal(cipherText);

        return new String(plainText, UTF_8);

    }
    
 // Password derived AES 256 bits secret key
    public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt)
        throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");                
        
        // iterationCount = 1000
        // keyLength = 256
        KeySpec spec = new PBEKeySpec(password, salt, ITERATION_COUNT,256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        String encodedKey = hex(secret.getEncoded());

        // print SecretKey as hex
        System.out.println("SecretKey: " + encodedKey);

        return factory.generateSecret(spec);

    }
    
 // hex representation
    public static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    
    
}
