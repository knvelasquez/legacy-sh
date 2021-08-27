/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Seguridad;

/**
 * @author Kevin Velásquez
 *
 */
public class CipherModel {
	private String encryptedKey;
	private String encryptedValue;
	
	/**
	 * @param key
	 * @param value
	 */
	public CipherModel(String key, String value) {
		this.encryptedKey = key;
		this.encryptedValue = value;
	}

	/**
	 * @return the key
	 */
	public String getEncrytedKey() {
		return encryptedKey;
	}

	/**
	 * @param key the key to set
	 */
	public void setEncryptedKey(String key) {
		this.encryptedKey = key;
	}

	/**
	 * @return the value
	 */
	public String getEncryptedValue() {
		return encryptedValue;
	}

	/**
	 * @param value the value to set
	 */
	public void setEncryptedValue(String value) {
		this.encryptedValue = value;
	}
}
