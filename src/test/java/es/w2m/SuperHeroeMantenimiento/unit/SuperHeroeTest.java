/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import es.w2m.SuperHeroeMantenimiento.ApiRestController.SuperHeroeApiRestControllerImpl;
import es.w2m.SuperHeroeMantenimiento.Respuesta.EntidadRespuesta;
import es.w2m.SuperHeroeMantenimiento.Solicitud.SuperHeroeSolicitud;
import es.w2m.SuperHeroeMantenimiento.config.Tiempo;
import es.w2m.SuperHeroeMantenimiento.enumerado.Color;
import es.w2m.SuperHeroeMantenimiento.enumerado.Habilidad;
import es.w2m.SuperHeroeMantenimiento.model.SuperHeroeModel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author Kevin Velásquez
 *
 */
public class SuperHeroeTest {
	static SuperHeroeApiRestControllerImpl superHeroeApi;	
	HttpServletResponse respuesta;
	static SuperHeroeModel batManMock;
	static SuperHeroeModel superManMock;
	static SuperHeroeModel spiderManMock;
	static SuperHeroeModel ironManMock;
	static SuperHeroeModel capitanAmericaMock;
	static SuperHeroeModel thorMock;
	static SuperHeroeModel hulkMock;
	
	@BeforeAll
	static void SetUpBeforeClass() throws Exception {
		// Arrange
		batManMock = new SuperHeroeModel(0, "Bat Man", "Bruce Wayne", "Ciudad Gotica", Habilidad.SuperIntelecto,
				"Murcielago", Color.Negro, "El Guason");

		superManMock = new SuperHeroeModel(1, "Super Man", "Clark Kent", "Metrópolis", Habilidad.SuperFuerza, "S",
				Color.Azul, "Lex Luthor");

		spiderManMock = new SuperHeroeModel(2, "Spider Man", "Peter Parker", "Ciudad de Nueva York", Habilidad.Telaraña,
				"Araña", Color.Rojo, "Duende Verde");

		ironManMock = new SuperHeroeModel(3, "Iron Man", "Tony Stark", "Mansión Stark", Habilidad.Volar, "Robot",
				Color.Rojo, "Ultron");

		capitanAmericaMock = new SuperHeroeModel(4, "Capitan America", "Steven Rogers", "Nueva York",
				Habilidad.SuperSoldado, "Estrella", Color.Azul, "Hydra");

		thorMock = new SuperHeroeModel(5, "Thor", "Dios del Trueno", "Asgard", Habilidad.CombateArmado, "Martillo",
				Color.Dorado, "Loki");

		hulkMock = new SuperHeroeModel(6, "Hulk", "Bruce Banner", "Sakaar", Habilidad.SuperFuerza, "Puño", Color.Verde,
				"Graviton");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {

	}

	@BeforeEach
	void setUp() throws Exception {
		superHeroeApi = mock(SuperHeroeApiRestControllerImpl.class);
	}

	@Test
	@DisplayName("Consultar Todos debería obtener una lista con información de todos los Súper Heroes encontrados")
	public void consultarTodosObtieneListaInformacionDeTodosLosSuperHeroes() {
		// Arrange
		List<SuperHeroeModel> listaSuperHeroes = new ArrayList<SuperHeroeModel>();
		listaSuperHeroes.add(batManMock);
		listaSuperHeroes.add(superManMock);
		listaSuperHeroes.add(spiderManMock);
		listaSuperHeroes.add(ironManMock);
		listaSuperHeroes.add(capitanAmericaMock);
		listaSuperHeroes.add(thorMock);
		listaSuperHeroes.add(hulkMock);

		EntidadRespuesta<List<SuperHeroeModel>> entidadRespuesta=new EntidadRespuesta<List<SuperHeroeModel>>(200,null,null,Tiempo.obtener());
		entidadRespuesta.setData(listaSuperHeroes);
		
		when(superHeroeApi.consultarTodos()).thenReturn(entidadRespuesta);

		// Act
		List<SuperHeroeModel> listaResultado = superHeroeApi.consultarTodos().getData();

		// Assert
		assertTrue(listaResultado.size() > 0);
		assertEquals("Bat Man", listaResultado.get(0).getNombre());
		assertEquals("Super Man", listaResultado.get(1).getNombre());
	}

	@Test
	@DisplayName("Consultar Todos debería obtener una lista vacía sin información")
	public void consultarTodosObtieneListaVaciaSinInformacion() {
		// Arrange
		List<SuperHeroeModel> listaSuperHeroes = new ArrayList<SuperHeroeModel>();
		EntidadRespuesta<List<SuperHeroeModel>> entidadRespuesta=new EntidadRespuesta<List<SuperHeroeModel>>(200,null,listaSuperHeroes,Tiempo.obtener());
		when(superHeroeApi.consultarTodos()).thenReturn(entidadRespuesta);

		// Act
		List<SuperHeroeModel> listaResultado = superHeroeApi.consultarTodos().getData();

		// Assert
		assertEquals(Collections.EMPTY_LIST, listaResultado);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			listaResultado.get(0);
		});
	}

	@Test
	@DisplayName("Consultar por Id debería obtener información por cada Súper Heroe indicado")
	public void consultarPorIdObtieneInformacionPorCadaSuperHeroeIndicado() {
		// Arrange
		EntidadRespuesta<SuperHeroeModel> entidadRespuesta=new EntidadRespuesta<SuperHeroeModel>(200,null,null,Tiempo.obtener());
		entidadRespuesta.setData(batManMock);
		
		when(superHeroeApi.consultarPorId(0)).thenReturn(entidadRespuesta);

		// Act
		SuperHeroeModel batmanResultado = superHeroeApi.consultarPorId(0).getData();

		// Assert
		assertEquals("Bat Man", batmanResultado.getNombre());
	}

	@Test
	@DisplayName("Consultar por Id debería obtener información vacía por cada Súper Heroe indicado")
	public void consultarPorIdObtieneVacioPorCadaSuperHeroeIndicado() {
		// Arrange
		EntidadRespuesta<SuperHeroeModel> entidadRespuesta=new EntidadRespuesta<SuperHeroeModel>(200,null,null,Tiempo.obtener());		
		when(superHeroeApi.consultarPorId(anyInt())).thenReturn(entidadRespuesta);

		// Act
		SuperHeroeModel superHeroeResultado1 = superHeroeApi.consultarPorId(7).getData();
		SuperHeroeModel superHeroeResultado2 = superHeroeApi.consultarPorId(8).getData();
		SuperHeroeModel superHeroeResultado3 = superHeroeApi.consultarPorId(9).getData();

		// Assert
		assertNull(superHeroeResultado1);
		assertNull(superHeroeResultado2);
		assertNull(superHeroeResultado3);
	}

	@Test
	@DisplayName("Consultar por nombre que contenga debería obtener una lista con información de todos los Súper Heroes encontrados")
	public void consultarPorNombreContengaObtieneListainformacionDeTodosLosSuperHeroes() {
		// Arrange
		List<SuperHeroeModel> listaSuperHeroe1 = new ArrayList<SuperHeroeModel>();
		List<SuperHeroeModel> listaSuperHeroe2 = new ArrayList<SuperHeroeModel>();

		listaSuperHeroe1.add(batManMock);
		listaSuperHeroe1.add(superManMock);
		listaSuperHeroe1.add(spiderManMock);
		listaSuperHeroe1.add(ironManMock);

		listaSuperHeroe2.add(superManMock);
		listaSuperHeroe2.add(spiderManMock);

		EntidadRespuesta<List<SuperHeroeModel>> entidadRespuesta=new EntidadRespuesta<List<SuperHeroeModel>>(200,null,null,Tiempo.obtener());
		entidadRespuesta.setData(listaSuperHeroe1);
		when(superHeroeApi.consultarPorNombreContenga("man")).thenReturn(entidadRespuesta);
		
		entidadRespuesta.setData(listaSuperHeroe2);
		when(superHeroeApi.consultarPorNombreContenga("er")).thenReturn(entidadRespuesta);

		// Act
		for (String nombreContiene : new String[] { "man", "er" }) {
			List<SuperHeroeModel> listaResultadoSuperHeroe = superHeroeApi.consultarPorNombreContenga(nombreContiene).getData();
			for (SuperHeroeModel superHeroe : listaResultadoSuperHeroe) {
				// Assert
				assertTrue(superHeroe.getNombre().toLowerCase().indexOf(nombreContiene) != -1);
			}
		}
	}

	@Test
	@DisplayName("Consultar por Nombre que contenga debería obtener una lista vacía")
	public void consultarPorNombreContengaObtieneListaVacia() {
		// Arrange
		List<SuperHeroeModel> listaSuperHeroe1 = new ArrayList<SuperHeroeModel>();
		EntidadRespuesta<List<SuperHeroeModel>> entidadRespuesta=new EntidadRespuesta<List<SuperHeroeModel>>(200,null,listaSuperHeroe1,Tiempo.obtener());
		when(superHeroeApi.consultarPorNombreContenga(anyString())).thenReturn(entidadRespuesta);

		// Act
		List<SuperHeroeModel> listaResultado = superHeroeApi.consultarPorNombreContenga("ork").getData();

		// Assert
		assertEquals(Collections.EMPTY_LIST, listaResultado);
	}

	@Test
	@DisplayName("Actualizar Súper Héroe debería modificar la información de un súper héroe indicado")
	public void modificarSuperHeroe() {
		// Arrange
		SuperHeroeSolicitud superHeroe = new SuperHeroeSolicitud();
		superHeroe.setIdentificacion(1);
		superHeroe.setNombre("nuevo-nombre");
		superHeroe.setIdentidadSecreta("nueva-identidad");

		EntidadRespuesta<SuperHeroeModel>entidadRespuesta=new EntidadRespuesta<SuperHeroeModel>(201,null,null,Tiempo.obtener());
		when(superHeroeApi.actualizar(superHeroe,respuesta)).thenReturn(entidadRespuesta);
		// Act

		EntidadRespuesta<SuperHeroeModel> entidadResultado = superHeroeApi.actualizar(superHeroe,respuesta);
		// Assert
		assertEquals(201, entidadResultado.getEstatus());
	}

	@Test
	@DisplayName("Eliminar Súper Héroe debería eliminar la información de un Súper héroe indicado")
	public void eliminarSuperHeroe() {
		// Arrange
		int identificacion=2;		

		EntidadRespuesta<SuperHeroeModel>entidadRespuesta=new EntidadRespuesta<SuperHeroeModel>(201,null,null,Tiempo.obtener());
		when(superHeroeApi.eliminar(identificacion,respuesta)).thenReturn(entidadRespuesta);
		// Act

		EntidadRespuesta<SuperHeroeModel> entidadResultado = superHeroeApi.eliminar(identificacion,respuesta);
		// Assert
		assertEquals(201, entidadResultado.getEstatus());
	}
}
