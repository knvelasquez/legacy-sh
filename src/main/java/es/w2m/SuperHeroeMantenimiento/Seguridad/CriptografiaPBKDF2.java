/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Seguridad;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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
}
