/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Solicitud;

/**
 * @author Kevin Velásquez
 *
 */
public class UsuarioSolicitud {

	private String usuario;
	private String contrasenia;

	/**
	 * @param usuario
	 * @param contrasenia
	 */
	public UsuarioSolicitud(String usuario, String contrasenia) {
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * @param contrasenia the contrasenia to set
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	@Override
	public String toString() {
		return String.format("Usuario: '%s' contrasenia **********", this.getUsuario());
	}

}
