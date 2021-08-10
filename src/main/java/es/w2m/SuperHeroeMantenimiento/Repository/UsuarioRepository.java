/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.w2m.SuperHeroeMantenimiento.model.UsuarioModel;

/**
 * @author Kevin Velásquez
 *
 */
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
	
	/**
	 * Este metodo es usado para obtener un usuario por medio de la identificacion
	 * 
	 * */
	public UsuarioModel findById(int id);

	/**
	 * Este metodo es usado para obtener un usuario por medio del usuario y contrasenia
	 * 
	 * */
	public UsuarioModel findByUsuarioAndContrasenia(String usuario, String contrasenia);
}
