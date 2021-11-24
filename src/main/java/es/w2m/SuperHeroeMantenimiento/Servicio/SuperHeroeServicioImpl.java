/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Servicio;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.w2m.SuperHeroeMantenimiento.Repository.SuperHeroeRepository;
import es.w2m.SuperHeroeMantenimiento.Respuesta.EntidadRespuesta;
import es.w2m.SuperHeroeMantenimiento.Solicitud.SuperHeroeSolicitud;
import es.w2m.SuperHeroeMantenimiento.config.Tiempo;
import es.w2m.SuperHeroeMantenimiento.model.SuperHeroeModel;

/**
 * @author Kevin Velásquez
 */
@Service
public class SuperHeroeServicioImpl implements SuperHeroeServicio {

	@Autowired
	private SuperHeroeRepository superHeroeRepository;

	/**
	 * Metodo para Obtener una lista con información
	 * de todos los Súper Héroes encontrados.
	 * */
	@Override
	public EntidadRespuesta<List<SuperHeroeModel>> consultarTodos() {	
		List<SuperHeroeModel> listaResultado=superHeroeRepository.findAll();		
		//Envia la entidad respuesta
		return new EntidadRespuesta<List<SuperHeroeModel>>(
				HttpServletResponse.SC_OK,
				"Total Súper Héroes encontrados "+ listaResultado.size(), 
				listaResultado,Tiempo.obtener());		
	}

	/**
	 * Metodo usado para Obtener información de cada Súper Héroe
	 * indicado por medio de un Id.
	 * */
	@Override
	public EntidadRespuesta<SuperHeroeModel> consultarPorId(int id) {
		//Envia la entidad respuesta
		return new EntidadRespuesta<SuperHeroeModel>(
				HttpServletResponse.SC_OK,
				"Súper Héroe encontrado", 
				superHeroeRepository.findById(id),Tiempo.obtener());				
	}

	/**
	 * Metodo usado para Obtener una lista con información
	 * de todos los Súper Héroes que contengan en su nombre el valor indicado.
	 * */
	@Override
	public EntidadRespuesta<List<SuperHeroeModel>> consultarPorNombreContenga(String nombre) {
		//Obtiene la informacion del servicio
		List<SuperHeroeModel> listaResultado=superHeroeRepository.findByNombreContainingIgnoreCase(nombre);
		//Envia la entidad respuesta
		return new EntidadRespuesta<List<SuperHeroeModel>>(
				HttpServletResponse.SC_OK,
				"Total Súper Héroes encontrados " + listaResultado.size(), 
				listaResultado,Tiempo.obtener());	
	}

	/**
	 * Metodo usado para Actualizar la información
	 * de un Súper Héroe indicado.
	 * */
	@Override
	public EntidadRespuesta<SuperHeroeModel> modificar(SuperHeroeSolicitud superHeroeSolicitud) {
		SuperHeroeModel superHeroeModel=null;
		//Valida si se ha indicado una identificacion para el usuario
		if(superHeroeSolicitud.getIdentificacion()==0) {
			return new EntidadRespuesta<SuperHeroeModel>(HttpServletResponse.SC_NOT_FOUND,
					"Súper Héroe no encontrado",superHeroeModel,Tiempo.obtener());
		}
		//Obtiene la informacion del super heroe por medio de la identificacion indicada
		superHeroeModel=superHeroeRepository.findById(superHeroeSolicitud.getIdentificacion()); 
		if(superHeroeModel==null) {
			return new EntidadRespuesta<SuperHeroeModel>(HttpServletResponse.SC_NOT_FOUND,
					"Súper Héroe no encontrado",superHeroeModel,Tiempo.obtener());
		}						
		//Valida que la informacion obtenida para actualizar no sea null
		if(superHeroeSolicitud.getNombre()!=null) {
			superHeroeModel.setNombre(superHeroeSolicitud.getNombre());
		}
		if(superHeroeSolicitud.getIdentidadSecreta()!=null) {
			superHeroeModel.setIdentidadSecreta(superHeroeSolicitud.getIdentidadSecreta());
		}			
		if(superHeroeSolicitud.getLugarResidencia()!=null) {
			superHeroeModel.setLugarResidencia(superHeroeSolicitud.getLugarResidencia());
		}
		if(superHeroeSolicitud.getSuperPoder()!=null) {
			superHeroeModel.setSuperPoder(superHeroeSolicitud.getSuperPoder());
		}
		if(superHeroeSolicitud.getLogo()!=null) {
			superHeroeModel.setLogo(superHeroeSolicitud.getLogo());
		}
		if(superHeroeSolicitud.getColor()!=null) {
			superHeroeModel.setColor(superHeroeSolicitud.getColor());
		}			
		if(superHeroeSolicitud.getArchiEnemigo()!=null) {
			superHeroeModel.setArchiEnemigo(superHeroeSolicitud.getArchiEnemigo());
		}											
		//Ejecuta la actualizacion en la base de datos
		superHeroeRepository.save(superHeroeModel);
		//Envia la entidad respuesta
		return new EntidadRespuesta<SuperHeroeModel>(HttpServletResponse.SC_ACCEPTED,
				"Súper Héroe modificado correctamente",superHeroeModel,Tiempo.obtener());		
	}

	/**
	 * Metodo usado para Eliminar la información
	 * de un Súper Héroe indicado.
	 * */
	@Override
	public EntidadRespuesta<SuperHeroeModel> eliminar(int identificacion) {
		SuperHeroeModel superHeroeModel=superHeroeRepository.findById(identificacion);
		if(superHeroeModel==null) {
			return new EntidadRespuesta<SuperHeroeModel>(HttpServletResponse.SC_NOT_FOUND,
					"Súper Héroe no encontrado",superHeroeModel,Tiempo.obtener());
		}	
		superHeroeRepository.delete(superHeroeModel);
		//Envia la entidad respuesta
		return new EntidadRespuesta<SuperHeroeModel>(HttpServletResponse.SC_ACCEPTED,
				"Súper Héroe eliminado correctamente",superHeroeModel,Tiempo.obtener());						
	}

	@Override
	public EntidadRespuesta<SuperHeroeModel> crear(SuperHeroeSolicitud superHeroeSolicitud) {
		SuperHeroeModel superHeroeModel=new SuperHeroeModel();
		superHeroeModel.setNombre(superHeroeSolicitud.getNombre());
		superHeroeModel.setLugarResidencia(superHeroeSolicitud.getLugarResidencia());
		superHeroeModel.setSuperPoder(superHeroeSolicitud.getSuperPoder());
		superHeroeModel.setSuperPoder(superHeroeSolicitud.getSuperPoder());
		superHeroeModel.setLogo(superHeroeSolicitud.getLogo());
		superHeroeModel.setColor(superHeroeSolicitud.getColor());
		superHeroeModel.setArchiEnemigo(superHeroeSolicitud.getArchiEnemigo());
		superHeroeModel.setIdentidadSecreta(superHeroeSolicitud.getIdentidadSecreta());
		superHeroeRepository.save(superHeroeModel);
		//Envia la entidad respuesta
		return new EntidadRespuesta<SuperHeroeModel>(HttpServletResponse.SC_ACCEPTED,
				"Súper Héroe creado correctamente",superHeroeModel,Tiempo.obtener());
	}
}
