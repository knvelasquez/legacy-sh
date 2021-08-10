/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import es.w2m.SuperHeroeMantenimiento.model.SuperHeroeModel;

/**
 * @author Kevin Velásquez
 *
 */
public interface SuperHeroeRepository extends JpaRepository<SuperHeroeModel,Long>{

	/**
	 * 
	 * 
	 * */
	SuperHeroeModel findById(int id);
	
	/**
	 * 
	 * 
	 * */
	List<SuperHeroeModel> findByNombreContainingIgnoreCase(String nombre);
	
}
