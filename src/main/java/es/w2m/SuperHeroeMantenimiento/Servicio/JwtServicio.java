/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Servicio;

import es.w2m.SuperHeroeMantenimiento.model.UsuarioModel;

/**
 * @author Kevin Velásquez
 *
 */
public interface JwtServicio {
	
	/**
	 * 
	 * 
	 * */
	public String generar(UsuarioModel usuario);
	
}
