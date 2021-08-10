/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.exception;

/**
 * @author Kevin Velásquez
 *
 */
public class EncabezadoNoEcontradoExeption extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EncabezadoNoEcontradoExeption(String mensaje) {
		super(mensaje);
	}
	
	public EncabezadoNoEcontradoExeption(String mensaje,Throwable causa) {
		super(mensaje,causa);
	}
}
