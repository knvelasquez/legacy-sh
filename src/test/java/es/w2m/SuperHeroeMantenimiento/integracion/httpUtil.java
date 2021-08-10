/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.integracion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import es.w2m.SuperHeroeMantenimiento.Respuesta.EntidadRespuesta;
import es.w2m.SuperHeroeMantenimiento.Solicitud.UsuarioSolicitud;
import es.w2m.SuperHeroeMantenimiento.model.SuperHeroeModel;
/**
 * @author Kevin Velásquez
 *
 */
public class httpUtil {
	@Autowired
	public MockMvc mvc;
	
	GsonBuilder gson = new GsonBuilder();	
	public enum http { GET, POST, PUT, DELETE,PATCH,OPTION}
	
	TypeToken<EntidadRespuesta<String>> mapTipoString = new TypeToken<EntidadRespuesta<String>>() {};
	TypeToken<EntidadRespuesta<List<SuperHeroeModel>>> mapTipoListaSuperHeroeModel=new TypeToken<EntidadRespuesta<List<SuperHeroeModel>>>() {};
	TypeToken<EntidadRespuesta<SuperHeroeModel>> mapTipoSuperHeroeModel=new TypeToken<EntidadRespuesta<SuperHeroeModel>>() {};
	
	String urlw2mUsuario="/w2m/usuario";
	String urlw2mSuperHeroe= "/w2m/superheroe";
	String urlw2mSuperHeroePorId= "/w2m/superheroe/:id";
	String urlw2mSuperHeroeNombreContenga= "/w2m/superheroe/nombre/:nombre";
	/**
	 * 
	 * 
	 * */
	protected <T1> EntidadRespuesta<T1> mvc(http _http,String url,String jwtToken,TypeToken<?> typeToken) throws UnsupportedEncodingException, Exception {
		return mvc(_http,url,jwtToken,null,typeToken);
	}
	protected <T1,T2> EntidadRespuesta<T1> mvc(http _http,String url,T2 body,TypeToken<?> typeToken) throws UnsupportedEncodingException, Exception {
		return mvc(_http,url,null,body,typeToken);
	}
	protected <T1,T2> EntidadRespuesta<T1> mvc(http _http,String url,String jwtToken,T2 body,TypeToken<?> typeToken) throws UnsupportedEncodingException, Exception {
		MockHttpServletRequestBuilder httpMock;
		switch (_http) {
        case GET:
        	httpMock=MockMvcRequestBuilders.get(url);
            break;
        case POST:
        	httpMock=MockMvcRequestBuilders.post(url);
            break;
        case PUT:
        	httpMock=MockMvcRequestBuilders.put(url);
        	break;
        case DELETE:
        	httpMock=MockMvcRequestBuilders.delete(url);
        	break;        	
        default:
        	httpMock=MockMvcRequestBuilders.get(url);
        	break;
		}
		//Set the headers		
		httpMock.contentType("application/json").accept("*/*");
		if(jwtToken!=null) {
			httpMock.header("Authorization", "Bearer ".concat(jwtToken));
		}
		if(body!=null) {
			httpMock.content(new Gson().toJson(body));
		}
		//Obtiene el json Resultado
		String json=mvc.perform(httpMock).andReturn().getResponse().getContentAsString();									
		//Envia la respuesta mapeada			
		return gson.setDateFormat("yyyy-mm-dd hh:mm:ss").create().fromJson(json,typeToken.getType());
	}	
	
	/**
	 * 
	 * 
	 * */
	protected String autenticar(UsuarioSolicitud usuario) throws UnsupportedEncodingException, Exception {
		return this.autenticar(usuario,200,true);
	}
	protected String autenticar(UsuarioSolicitud usuario,int estatus) throws UnsupportedEncodingException, Exception {
		return this.autenticar(usuario,estatus,true);
	}
	protected String autenticar(UsuarioSolicitud usuario,int estatus,Boolean notNull) throws UnsupportedEncodingException, Exception {
		//Assert
		EntidadRespuesta<String> autenticacionResultado=mvc(http.POST,urlw2mUsuario, usuario, mapTipoString);
		//Act
		String jwtToken=autenticacionResultado.getData();
		//Assert		
		assertEquals(estatus,autenticacionResultado.getEstatus(),String.format("'%s': %s",usuario.getUsuario(),autenticacionResultado.getDescripcion()));
		if(notNull) {
			assertNotEquals(null,jwtToken,String.format("'%s token null", usuario.getUsuario()));
		}
		else {
			assertEquals(null,jwtToken,String.format("'%s token null", usuario.getUsuario()));
		}
		//Envia el token
		return jwtToken;
	}
}
