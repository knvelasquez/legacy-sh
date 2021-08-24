/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.ApiRestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import es.w2m.SuperHeroeMantenimiento.ExternalScheduler;
import es.w2m.SuperHeroeMantenimiento.Solicitud.HashSolicitud;
import io.swagger.annotations.Api;

/**
 * @author Kevin Velásquez
 *
 */
@RestController
@Api(tags = "Hash")
public class HashApiRestControllerImpl implements HashApiRestController{
	
	@Autowired
	private ExternalScheduler externalScheduler;		
	
	/**
	 * 
	 * 
	 * */
	@Override
	public String hash(HashSolicitud hashModel) {					
		externalScheduler.addJob(hashModel.getValor());			   
		return externalScheduler.list.toString();
	}

	@Override
	public String get_hash() {
		return externalScheduler.list.toString();
	}	
}
