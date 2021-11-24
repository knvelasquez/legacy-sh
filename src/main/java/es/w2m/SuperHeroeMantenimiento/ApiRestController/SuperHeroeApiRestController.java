/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.ApiRestController;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sun.istack.NotNull;
import es.w2m.SuperHeroeMantenimiento.Respuesta.EntidadRespuesta;
import es.w2m.SuperHeroeMantenimiento.Solicitud.SuperHeroeSolicitud;
import es.w2m.SuperHeroeMantenimiento.model.SuperHeroeModel;
import io.swagger.annotations.ApiOperation;

/**
 * @author Kevin Velásquez
 *
 */
public interface SuperHeroeApiRestController {
	
	/*@PreAuthorize:Es una anotación más nueva que @Secured (disponible desde la versión 3 de Spring Security) 
	 * mucho más flexible.
	 * Sintaxis: "hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')
	 *
	 */
	@ApiOperation(value = "Este método es usado para Obtener una lista con información de todos los Súper Héroes encontrados.")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPERHEROE_CONSULTARTODOS')")	
	@RequestMapping(value="superheroe",method = RequestMethod.GET)	
	public EntidadRespuesta<List<SuperHeroeModel>> consultarTodos();		
	
	@ApiOperation(value = "Este método es usado para Obtener información de cada Súper Héroe indicado por medio de un Id.")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPERHEROE_CONSULTARPORID')")	
	@RequestMapping(value="superheroe/{identificacion}",method = RequestMethod.GET)
	public EntidadRespuesta<SuperHeroeModel> consultarPorId(@PathVariable @NotNull int identificacion);

	@ApiOperation(value = "Este método es usado para Obtener una lista con información de todos los Súper Héroes que contengan en su nombre el valor indicado.")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPERHEROE_CONSULTARNOMBRECONTENGA')")	
	@RequestMapping(value="superheroe/nombre/{nombre}",method = RequestMethod.GET)
	public EntidadRespuesta<List<SuperHeroeModel>> consultarPorNombreContenga(@PathVariable @NotNull String nombre);
	
	@ApiOperation(value = "Este método es usado para Actualizar la información de un Súper Héroe indicado.")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPERHEROE_MODIFICAR')")	
	@RequestMapping(value="superheroe",method = RequestMethod.PUT)
	public EntidadRespuesta<SuperHeroeModel> actualizar(@RequestBody SuperHeroeSolicitud superHeroeSolicitud,HttpServletResponse respuesta);
	
	@ApiOperation(value = "Este método es usado para Eliminar la información de un Súper Héroe indicado.")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPERHEROE_ELIMINAR')")	
	@RequestMapping(value="superheroe/{identificacion}",method = RequestMethod.DELETE)
	public EntidadRespuesta<SuperHeroeModel> eliminar(@PathVariable @NotNull int identificacion,HttpServletResponse respuesta);

	@ApiOperation(value = "Este método es usado para crear un nuevo Súper Héroe.")
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_SUPERHEROE_CONSULTARTODOS')")
	@RequestMapping(value="superheroe",method = RequestMethod.POST)
	public EntidadRespuesta<SuperHeroeModel> crear(@RequestBody SuperHeroeSolicitud superHeroeSolicitud,HttpServletResponse respuesta);
}
