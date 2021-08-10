/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.ApiRestController;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.w2m.SuperHeroeMantenimiento.Respuesta.EntidadRespuesta;
import es.w2m.SuperHeroeMantenimiento.Solicitud.UsuarioSolicitud;
import io.swagger.annotations.ApiOperation;

/**
 * @author Kevin Velásquez
 *
 */
public interface UsuarioApiRestController {
	
	/**
	 * Método utilizado para autenticar un usuario por medio del usuario y contrasenia
	 * debe retornar un Jwt Token con la informacion del usuario autenticado
	 * */
	@ApiOperation(value = "Este método es usado para Autenticar un usuario debería obtener un jwt token con la información del usuario autenticado.")
	@RequestMapping(value="usuario",method = RequestMethod.POST)
	public EntidadRespuesta<String> autenticar(@RequestBody UsuarioSolicitud usuario,HttpServletResponse respuesta);
	
}
