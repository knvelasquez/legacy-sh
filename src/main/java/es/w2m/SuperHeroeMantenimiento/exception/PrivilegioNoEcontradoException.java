/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.exception;

/**
 * @author Kevin Velásquez
 *
 */
public class PrivilegioNoEcontradoException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PrivilegioNoEcontradoException(String mensaje) {
		super(mensaje);
	}
	
	public PrivilegioNoEcontradoException(String mensaje,Throwable causa) {
		super(mensaje,causa);
	}
}
