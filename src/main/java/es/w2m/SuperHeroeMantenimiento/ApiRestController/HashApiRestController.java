/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.ApiRestController;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sun.istack.NotNull;
import es.w2m.SuperHeroeMantenimiento.Respuesta.EntidadRespuesta;
import es.w2m.SuperHeroeMantenimiento.Respuesta.HashClavePublicaRespuesta;
import es.w2m.SuperHeroeMantenimiento.Respuesta.JobTokenRespuesta;
import es.w2m.SuperHeroeMantenimiento.Solicitud.HashClavePrivadaSolicitud;
import es.w2m.SuperHeroeMantenimiento.Solicitud.HashClavePublicaSolicitud;

/**
 * @author Kevin Velásquez
 *
 */
public interface HashApiRestController {

	/**
	 * 
	 * 
	 * */
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "hash2", method = RequestMethod.POST, 
	consumes = {MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE},
	produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public EntidadRespuesta<HashClavePublicaRespuesta> hash(@RequestBody @NotNull HashClavePublicaSolicitud clavePublica);
	
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
		@CrossOrigin(origins = "*")
		@RequestMapping(value = "hash", method = RequestMethod.POST, 
		consumes = {MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_FORM_URLENCODED_VALUE},
		produces = {MediaType.APPLICATION_JSON_VALUE}
		)
	public EntidadRespuesta<HashClavePublicaRespuesta> hash2(HashClavePublicaSolicitud clavePublica);

	@RequestMapping(value = "hash", method = RequestMethod.GET)
	public EntidadRespuesta<List<JobTokenRespuesta>> get_hash();
	
	@RequestMapping(value = "/{token}/hash", method = RequestMethod.PUT)
	public EntidadRespuesta<String> hash(@PathVariable String token,@RequestBody @NotNull HashClavePrivadaSolicitud clavePrivada);
	
}
