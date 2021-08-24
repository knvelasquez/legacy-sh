/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.ApiRestController;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sun.istack.NotNull;
import es.w2m.SuperHeroeMantenimiento.Solicitud.HashSolicitud;

/**
 * @author Kevin Velásquez
 *
 */
public interface HashApiRestController {
	
	/**
	 * 
	 * 
	 * */
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="hash",method = RequestMethod.POST)
	public String hash(@RequestBody @NotNull HashSolicitud hashModel);
	
	@RequestMapping(value="hash",method = RequestMethod.GET)
	public String get_hash();
}
