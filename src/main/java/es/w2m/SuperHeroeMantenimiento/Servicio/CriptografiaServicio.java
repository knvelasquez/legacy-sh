/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Servicio;

import es.w2m.SuperHeroeMantenimiento.Respuesta.EntidadRespuesta;

/**
 * @author Kevin Velásquez
 *
 */
public interface CriptografiaServicio {

	/**
	 * 
	 * 
	 * */
	public EntidadRespuesta<String> cifrar(String valor);
	
}
