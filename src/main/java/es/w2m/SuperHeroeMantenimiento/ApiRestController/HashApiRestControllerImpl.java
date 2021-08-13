/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.ApiRestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.web.bind.annotation.RestController;

import es.w2m.SuperHeroeMantenimiento.Seguridad.CriptografiaPBKDF2;
import es.w2m.SuperHeroeMantenimiento.Solicitud.HashSolicitud;
import io.swagger.annotations.Api;

/**
 * @author Kevin Velásquez
 *
 */
@RestController
@Api(tags = "Hash")
public class HashApiRestControllerImpl implements HashApiRestController{
	
	/**
	 * 
	 * 
	 * */
	@Override
	public String get_hash(HashSolicitud hashModel) {	
		String result;
		try {
			result=CriptografiaPBKDF2.cifrar(hashModel.getValor());
		} catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
			result=exception.getMessage();
		}	
		return result;
	}	
}
