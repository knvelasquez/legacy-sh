/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Seguridad;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.w2m.SuperHeroeMantenimiento.Respuesta.EntidadRespuesta;
import es.w2m.SuperHeroeMantenimiento.exception.EncabezadoNoEcontradoExeption;
import es.w2m.SuperHeroeMantenimiento.exception.ExceptionGlobalHandler;
import es.w2m.SuperHeroeMantenimiento.exception.PrivilegioNoEcontradoException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

/**
 * Clase para filtrar los recursos y solo permitir el acceso
 * por medio de un Jwt Token
 * */
public class JWTFiltroAuthorization extends OncePerRequestFilter {
	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	private final String SECRET = "udmVsYXNxdWV6IiwibmFtZSI6";

	@SuppressWarnings("rawtypes")
	@Override
	public void doFilterInternal(HttpServletRequest solicitud, HttpServletResponse respuesta,
			FilterChain cadenaDeFiltros) throws ServletException, IOException {
		try {
			if (existeJwtToken(solicitud, respuesta)) {
				Claims claims = validarJWToken(solicitud);
				// Validacion de los privilegios
				if (claims.get("authorities") != null && ((List) claims.get("authorities")).size() > 0) {
					setAutenticacionDentroDelFlujoSpring(claims);
				} else {
					SecurityContextHolder.clearContext();
					throw new PrivilegioNoEcontradoException(
							"El Usuario indicado no Posee Privilegios para poder continuar, favor contactar al administrador.");
				}
			} else {
				SecurityContextHolder.clearContext();
				// Validacion que permite obtener acceso al recurso indicado
				if (!solicitud.getRequestURI().equals("/w2m/usuario") 
					&& !solicitud.getRequestURI().equals("/hash")) {
					throw new EncabezadoNoEcontradoExeption(
							"No ha sido validado correctamente el atributo Authorization en el header indicado.");
				}
			}
			cadenaDeFiltros.doFilter(solicitud, respuesta);
		} catch (ExpiredJwtException excepcion) {
			this.estableceEntidadRespuesta(respuesta,
					new ExceptionGlobalHandler().expiredJwtException((ExpiredJwtException) excepcion));
			return;
		} catch (MalformedJwtException excepcion) {
			this.estableceEntidadRespuesta(respuesta, new ExceptionGlobalHandler().malFormedException(excepcion));
			return;
		} catch (EncabezadoNoEcontradoExeption excepcion) {
			this.estableceEntidadRespuesta(respuesta,
					new ExceptionGlobalHandler().encabezadoNoEncontradoException(excepcion));
			return;
		} catch (PrivilegioNoEcontradoException excepcion) {			
			this.estableceEntidadRespuesta(respuesta,
					new ExceptionGlobalHandler().privilegioNoEncontradoException(excepcion));
			return;
		}
	}
	
	/**
	 * Metodo para establecer el contenido de la respuesta
	 * 
	 */
	protected void estableceEntidadRespuesta(HttpServletResponse respuesta,
			ResponseEntity<EntidadRespuesta<String>> entidadRespuesta) throws IOException {
		//Establece el content type con el formato json
		respuesta.setHeader("Content-Type", "application/json");
		respuesta.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		//envia la respuesta				
		respuesta.getOutputStream().write(new ObjectMapper().writeValueAsString(entidadRespuesta.getBody()).getBytes());
	}
	
	/**
	 * Metodo para autenticarnos dentro del flujo de Spring
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setAutenticacionDentroDelFlujoSpring(Claims claims) {
		List<String> PrivilegiosOtorgados = (List) claims.get("authorities");
		//
		UsernamePasswordAuthenticationToken authorizacion = new UsernamePasswordAuthenticationToken(claims.getSubject(),
				null, PrivilegiosOtorgados.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		//
		SecurityContextHolder.getContext().setAuthentication(authorizacion);
	}

	/**
	 * Metodo para deserializar el token y validarlo
	 * 
	 */
	private Claims validarJWToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	/**
	 * Metodo para validar si existe el encabezado Authrization dentro de la solicitud
	 * 
	 */
	private boolean existeJwtToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return false;
		return true;
	}
}
