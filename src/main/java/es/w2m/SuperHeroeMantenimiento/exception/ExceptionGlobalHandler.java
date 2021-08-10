/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.exception;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import es.w2m.SuperHeroeMantenimiento.Respuesta.EntidadRespuesta;
import es.w2m.SuperHeroeMantenimiento.config.Tiempo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

/**
 * @author Kevin Velásquez
 *
 */
@ControllerAdvice
public class ExceptionGlobalHandler {		
	
	/**
	 * 
	 * Método Para Manejar Excepciones En tiempo de Ejecucion
	 * */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<EntidadRespuesta<String>> runtimeException(RuntimeException excepcion) {
		return new ResponseEntity<>(
				new EntidadRespuesta<String>(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
											  excepcion.getMessage(),null,
											  Tiempo.obtener()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Método Para Manejar Excepciones
	 * 
	 * */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<EntidadRespuesta<String>> exception(Exception excepcion) {
		return new ResponseEntity<>(
				new EntidadRespuesta<String>(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				    excepcion.getMessage(),null,
					Tiempo.obtener()), HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
	/**
	 * Método Para Manejar Excepciones de Tipo Acceso Denegado
	 * 
	 * */
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<EntidadRespuesta<String>> accessDeniedException(AccessDeniedException excepcion) {
		return new ResponseEntity<>(
				new EntidadRespuesta<String>(HttpServletResponse.SC_UNAUTHORIZED,
						"Acceso denegado, Favor contactar con el administrador",null,
						Tiempo.obtener()), HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Método Para Manejar Excepciones de Tipo Jwt Expirado
	 * 
	 * */
	@ExceptionHandler(ExpiredJwtException.class)
	public  ResponseEntity<EntidadRespuesta<String>> expiredJwtException(ExpiredJwtException excepcion){
		return new ResponseEntity<>(
				new EntidadRespuesta<String>(HttpServletResponse.SC_UNAUTHORIZED,
					"El jwt Token ha expirado, Usted debe autenticarse para generar uno nuevo.",null,
					Tiempo.obtener()), HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Método Para Manejar Excepciones de Jwt Mal Formado
	 * 
	 * */
	@ExceptionHandler(MalformedJwtException.class)
	public ResponseEntity<EntidadRespuesta<String>> malFormedException(MalformedJwtException excepcion){
		return new ResponseEntity<>(
				new EntidadRespuesta<String>(HttpServletResponse.SC_UNAUTHORIZED,
					"La cadena JWT está mal formada, revisar que contenga la longitud y la cantidad de carácteres adecuada.",null,
					Tiempo.obtener()), HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Método Para Manejar Excepciones de Tipo Encabezado No Encontrado
	 * 
	 * */
	@ExceptionHandler(EncabezadoNoEcontradoExeption.class)
	public ResponseEntity<EntidadRespuesta<String>> encabezadoNoEncontradoException(EncabezadoNoEcontradoExeption excepcion){
		return new ResponseEntity<>(
				new EntidadRespuesta<String>(HttpServletResponse.SC_UNAUTHORIZED,
					excepcion.getMessage(),null,
					Tiempo.obtener()), HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Método Para Manejar Excepciones de Tipo Privilegio No Encontrado
	 * 
	 * */
	@ExceptionHandler(PrivilegioNoEcontradoException.class)
	public ResponseEntity<EntidadRespuesta<String>> privilegioNoEncontradoException(PrivilegioNoEcontradoException excepcion){
		return new ResponseEntity<>(
				new EntidadRespuesta<String>(HttpServletResponse.SC_UNAUTHORIZED,
					excepcion.getMessage(),null,
					Tiempo.obtener()), HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * Método Para Manejar Excepciones de Tipo No existe Algoritmo
	 * 
	 * */
	@ExceptionHandler(NoSuchAlgorithmException.class)
	public ResponseEntity<EntidadRespuesta<String>> noSuchAlgorithmException(NoSuchAlgorithmException excepcion){
		return new ResponseEntity<>(
				new EntidadRespuesta<String>(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					excepcion.getMessage(),null,
					Tiempo.obtener()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Método Para Manejar Excepciones de Tipo Clave Iválida en la Especificación
	 * 
	 * */
	@ExceptionHandler(InvalidKeySpecException.class)
	public ResponseEntity<EntidadRespuesta<String>> invalidKeySpecException(InvalidKeySpecException excepcion){
		return new ResponseEntity<>(
				new EntidadRespuesta<String>(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					excepcion.getMessage(),null,
					Tiempo.obtener()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
}
