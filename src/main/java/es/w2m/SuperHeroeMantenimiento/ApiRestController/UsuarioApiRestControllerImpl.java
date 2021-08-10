/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.ApiRestController;

import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.w2m.SuperHeroeMantenimiento.AnotacionPersonalizada.TotalTiempoEjecucion;
import es.w2m.SuperHeroeMantenimiento.Respuesta.EntidadRespuesta;
import es.w2m.SuperHeroeMantenimiento.Servicio.UsuarioServicio;
import es.w2m.SuperHeroeMantenimiento.Solicitud.UsuarioSolicitud;
import io.swagger.annotations.Api;

/**
 * @author Kevin Velásquez
 *
 */
@RestController
@RequestMapping("/w2m")
@Api(tags = "Usuario")
public class UsuarioApiRestControllerImpl implements UsuarioApiRestController {

	private static final Logger logger = LogManager.getLogger(UsuarioApiRestController.class);
	
	@Autowired
	private UsuarioServicio usuarioServicio;

	/**
	 * Método utilizado para autentica un usuario por medio del usuario y contrasenia
	 * debe retornar un Jwt Token con la informacion del usuario autenticado
	 * */
	@TotalTiempoEjecucion
	public EntidadRespuesta<String> autenticar(UsuarioSolicitud usuario,HttpServletResponse respuesta) {
		logger.info("Iniciando Autenticación");
		logger.debug(usuario.toString());
		
		EntidadRespuesta<String> resultadoAutenticacion=usuarioServicio.autenticar(usuario);		
		respuesta.setStatus(resultadoAutenticacion.getEstatus());
		
		logger.info("Fin Autenticación");
		logger.debug(resultadoAutenticacion.toString());
		return resultadoAutenticacion;		
	}
}
