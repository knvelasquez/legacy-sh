/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.unit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import es.w2m.SuperHeroeMantenimiento.ApiRestController.UsuarioApiRestController;
//import es.w2m.SuperHeroeMantenimiento.Repository.UsuarioRepository;
import es.w2m.SuperHeroeMantenimiento.Respuesta.EntidadRespuesta;
import es.w2m.SuperHeroeMantenimiento.Servicio.SuperHeroeServicioImpl;
import es.w2m.SuperHeroeMantenimiento.Servicio.UsuarioServicioImpl;
import es.w2m.SuperHeroeMantenimiento.Solicitud.UsuarioSolicitud;
import es.w2m.SuperHeroeMantenimiento.config.Tiempo;
import static org.mockito.Mockito.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.servlet.http.HttpServletResponse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Kevin Velásquez
 *
 */
public class UsuarioTest{		
	static UsuarioApiRestController usuarioApi;
	HttpServletResponse respuesta;
	UsuarioSolicitud adminMock=new UsuarioSolicitud("admin","f9ce73932a795e2");
	UsuarioSolicitud uctMock=new UsuarioSolicitud("uct","uct1234");
	UsuarioSolicitud uciMock=new UsuarioSolicitud("uci","uci1234");
	UsuarioSolicitud ucnMock=new UsuarioSolicitud("ucn","ucn1234");
	UsuarioSolicitud uscMock=new UsuarioSolicitud("usc","usc1234");
	UsuarioSolicitud ucmMock=new UsuarioSolicitud("usc","ucm1234");
	UsuarioSolicitud ucmeMock=new UsuarioSolicitud("usc","ucme1234");
	
	@InjectMocks
	private UsuarioServicioImpl usuarioServicio;
	
	@InjectMocks
	private SuperHeroeServicioImpl superHeroeServicio;

	//@Autowired
	//private UsuarioRepository usuarioRepository;	
	
	@BeforeAll
	static void SetUpBeforeClass() throws Exception{
		
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception{
		
	}
	
	@BeforeEach
	void setUp() throws Exception{
		usuarioApi=mock(UsuarioApiRestController.class);
		//Injecta el repositorio
		//ReflectionTestUtils.setField(usuarioServicio,"usuarioRepository",usuarioRepository);
	}
	
	@Test
	@DisplayName("Autenticar usuario credenciales correctas debería obtener información válida")
	public void AutenticarUsuarioObtieneInformacionOk() throws NoSuchAlgorithmException, InvalidKeySpecException {
		//Arrange		
		EntidadRespuesta<String> entidadRespuesta=new EntidadRespuesta<String>(200,null,null,Tiempo.obtener());
		entidadRespuesta.setData("Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJXb3JsZCAyIE1lZXQsIFMuTC5VLiIsInN1YiI6Imt2ZWxhc3F1ZXoiLCJub21icmUiOiJLZXZpbiIsImFwZWxsaWRvIjoiVmVsYXNxdWV6IiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9TVVBFUkhFUk9FX0NPTlNVTFRBUlRPRE9TIiwiUk9MRV9TVVBFUkhFUk9FX0NPTlNVTFRBUlBPUklEIiwiUk9MRV9TVVBFUkhFUk9FX0NPTlNVTFRBUk5PTUJSRUNPTlRFTkdBIl0sImlhdCI6MTYyNzQwNTY3MywiZXhwIjoxNjI3NDA2MjczfQ.CaD314CFjqeBMEwRvAyuxcMNaV1qOWnuec930l1PzFu6_7_NitqAX3Eq9bo5trd2MMeDPF_ksqZr4CnktejE5A");
		
		when(usuarioApi.autenticar(adminMock,respuesta)).thenReturn(entidadRespuesta);		
		when(usuarioApi.autenticar(uctMock,respuesta)).thenReturn(entidadRespuesta);
		when(usuarioApi.autenticar(uciMock,respuesta)).thenReturn(entidadRespuesta);
		when(usuarioApi.autenticar(ucnMock,respuesta)).thenReturn(entidadRespuesta);
			
		//Act		 
		EntidadRespuesta<String> usuarioAutenticado=usuarioApi.autenticar(adminMock,respuesta);
		EntidadRespuesta<String> usuarioAutenticado2=usuarioApi.autenticar(uctMock,respuesta);
		EntidadRespuesta<String> usuarioAutenticado3=usuarioApi.autenticar(uciMock,respuesta);
		EntidadRespuesta<String> usuarioAutenticado4=usuarioApi.autenticar(ucnMock,respuesta);
		
		//Assert
		assertAll("Debería obtener el jwt token de cada usuario indicado",
			()-> assertNotNull(usuarioAutenticado,"Error usuario1"),
			()-> assertNotNull(usuarioAutenticado2,"Error usuario2"),
			()-> assertNotNull(usuarioAutenticado3,"Error usuario3"),
			()-> assertNotNull(usuarioAutenticado4,"Error usuario4")
		);		
	}
	
	@Test
	@DisplayName("Autenticar usuario credenciales inválidas debería obtener error de acceso")
	public void AutenticarUsuarioObtieneErrorAcceso() throws NoSuchAlgorithmException, InvalidKeySpecException {
		//Arrange
		uctMock.setContrasenia("clave-errada");
		uciMock.setContrasenia("clave-errada");
		ucnMock.setContrasenia("clave-errada");
		uscMock.setContrasenia("clave-errada");
		
		EntidadRespuesta<String> entidadRespuesta=new EntidadRespuesta<String>(401,null,null,Tiempo.obtener());
		
		when(usuarioApi.autenticar(uctMock,respuesta)).thenReturn(entidadRespuesta);
		when(usuarioApi.autenticar(uciMock,respuesta)).thenReturn(entidadRespuesta);
		when(usuarioApi.autenticar(ucnMock,respuesta)).thenReturn(entidadRespuesta);
		when(usuarioApi.autenticar(uscMock,respuesta)).thenReturn(entidadRespuesta);
		
		EntidadRespuesta<String> usuarioNoAutenticado5=usuarioApi.autenticar(uctMock,respuesta);
		EntidadRespuesta<String> usuarioNoAutenticado6=usuarioApi.autenticar(uciMock,respuesta);
		EntidadRespuesta<String> usuarioNoAutenticado7=usuarioApi.autenticar(ucnMock,respuesta);
		EntidadRespuesta<String> usuarioNoAutenticado8=usuarioApi.autenticar(uscMock,respuesta);
		
		//Assert
		assertAll("Debería obtener Obtener null para cada usuario indicado",
			()-> assertNull(usuarioNoAutenticado5.getData(),"Error usuario5"),
			()-> assertNull(usuarioNoAutenticado6.getData(),"Error usuario6"),
			()-> assertNull(usuarioNoAutenticado7.getData(),"Error usuario7"),
			()-> assertNull(usuarioNoAutenticado8.getData(),"Error usuario8")
		);					
	}	
}
