/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Respuesta;

/**
 * @author Kevin Velásquez
 *
 */
public class HashClavePublicaRespuesta {
	
	public String token;
		

	/**
	 * @param token
	 */
	public HashClavePublicaRespuesta(String token) {
		this.token = token;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

}
