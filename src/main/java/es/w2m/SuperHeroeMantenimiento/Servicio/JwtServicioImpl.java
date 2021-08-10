/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import es.w2m.SuperHeroeMantenimiento.model.PrivilegioModel;
import es.w2m.SuperHeroeMantenimiento.model.UsuarioModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Kevin Velásquez
 *
 */
@Service
public class JwtServicioImpl implements JwtServicio {

	/**
	 * Método para generar un Jwt Token con el usuario autenticado
	 * 
	 * */
	@Override
	public String generar(UsuarioModel usuario) {
		String claveSecreta = "udmVsYXNxdWV6IiwibmFtZSI6";			
		List<String> listaPrivilegio=new ArrayList<String>();
		for(PrivilegioModel privilegio :  usuario.getPrivilegio()){					
			listaPrivilegio.add(privilegio.getValor());
		}		
		List<GrantedAuthority> privilegiosOtorgados = AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", listaPrivilegio));			
		String jwtToken = Jwts.builder()
							  //.setId("@ID-JWT")
							  .setIssuer("World 2 Meet, S.L.U.")
							  .setSubject(usuario.getUsuario())
							  .claim("nombre", usuario.getNombre())
							  .claim("apellido", usuario.getApellido())
							  //.claim("identificacion", usuario.getId())
							  .claim("authorities",privilegiosOtorgados.stream()
									  .map(GrantedAuthority::getAuthority)
									  .collect(Collectors.toList()))
							  .setIssuedAt(new Date(System.currentTimeMillis()))
							  .setExpiration(new Date(System.currentTimeMillis() + 600000))
							  .signWith(SignatureAlgorithm.HS512,
							  claveSecreta.getBytes()).compact();
		//Envia el Jwt Token armado
		return jwtToken;	
	}

}
