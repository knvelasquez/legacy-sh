/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Servicio;

import es.w2m.SuperHeroeMantenimiento.Respuesta.EntidadRespuesta;
import es.w2m.SuperHeroeMantenimiento.Solicitud.UsuarioSolicitud;

/**
 * @author Kevin Velásquez
 *
 */
public interface UsuarioServicio {
	/**
	 * 
	 * 
	 * */	
	public EntidadRespuesta<String> autenticar(UsuarioSolicitud usuario);
}
