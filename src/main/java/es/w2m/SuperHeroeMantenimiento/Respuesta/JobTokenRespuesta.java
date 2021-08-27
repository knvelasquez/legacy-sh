/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Respuesta;

/**
 * @author Kevin Velásquez
 *
 */
public class JobTokenRespuesta {

	private String token;
	private String text;
	private long alive;		

	/**
	 * @param token
	 * @param text
	 * @param alive
	 */
	public JobTokenRespuesta(String token, String text, long alive) {
		this.token = token;
		this.text = text;
		this.alive = alive;
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

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the alive
	 */
	public long getAlive() {
		return alive;
	}

	/**
	 * @param alive the alive to set
	 */
	public void setAlive(long alive) {
		this.alive = alive;
	}

}
