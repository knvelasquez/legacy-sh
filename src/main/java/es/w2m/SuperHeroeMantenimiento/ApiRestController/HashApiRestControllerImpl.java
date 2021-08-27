/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.ApiRestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import es.w2m.SuperHeroeMantenimiento.ExternalScheduler;
import es.w2m.SuperHeroeMantenimiento.Respuesta.EntidadRespuesta;
import es.w2m.SuperHeroeMantenimiento.Respuesta.HashClavePublicaRespuesta;
import es.w2m.SuperHeroeMantenimiento.Respuesta.JobTokenRespuesta;
import es.w2m.SuperHeroeMantenimiento.Seguridad.CipherSecurity;
import es.w2m.SuperHeroeMantenimiento.Solicitud.HashClavePrivadaSolicitud;
import es.w2m.SuperHeroeMantenimiento.Solicitud.HashClavePublicaSolicitud;
import es.w2m.SuperHeroeMantenimiento.config.Tiempo;
import es.w2m.SuperHeroeMantenimiento.model.JobTokenModel;
import es.w2m.SuperHeroeMantenimiento.util.DateUtil;
import io.swagger.annotations.Api;

/**
 * @author Kevin Velásquez
 *
 */
@RestController
@Api(tags = "Hash")
public class HashApiRestControllerImpl implements HashApiRestController {

	@Autowired
	private ExternalScheduler externalScheduler;

	@Autowired
	private CipherSecurity cipher;

	/**
	 * Name: has
	 * 
	 */
	@Override
	public EntidadRespuesta<HashClavePublicaRespuesta> hash(HashClavePublicaSolicitud clavePublica) {
		EntidadRespuesta<HashClavePublicaRespuesta> result = new EntidadRespuesta<HashClavePublicaRespuesta>();
		result.setTiempo(Tiempo.obtener());
		// Validate if already exist to evit duplicates
		boolean ifExist = false;
		for (Map.Entry<String, JobTokenModel> job : externalScheduler.listJobToken.entrySet()) {
			if (job.getValue().getText().equals(clavePublica.getValor())) {
				ifExist = true;
				break;
			}
		}
		if (ifExist) {
			result.setEstatus(HttpServletResponse.SC_BAD_REQUEST);
			result.setDescripcion("Error Public key already exist");
			return result;
		}
		// Set the encryptedToken
		String encryptedToken = cipher.encrypt(String.format("new-put-url(%s)", DateUtil.getCurrentDate()))
				.getEncryptedValue();
		externalScheduler.addJob(encryptedToken, clavePublica.getValor());

		// send the response
		result.setEstatus(HttpServletResponse.SC_OK);
		result.setDescripcion("Public key Established");
		result.setData(new HashClavePublicaRespuesta(encryptedToken));
		return result;
	}

	@Override
	public EntidadRespuesta<List<JobTokenRespuesta>> get_hash() {
		List<JobTokenRespuesta> jobTokenList = new ArrayList<JobTokenRespuesta>();
		//
		for (Map.Entry<String, JobTokenModel> job : externalScheduler.listJobToken.entrySet()) {
			jobTokenList.add(
					new JobTokenRespuesta(job.getKey(), job.getValue().getText(), job.getValue().getSecondScheduled()
							- DateUtil.getDifferenceFromNowinSeconds(job.getValue().getCreated())));
		}
		return new EntidadRespuesta<List<JobTokenRespuesta>>(HttpServletResponse.SC_OK, "JobSchedule List",
				jobTokenList, Tiempo.obtener());
	}

	@Override
	public EntidadRespuesta<String> hash(String token, HashClavePrivadaSolicitud clavePrivada) {		
		EntidadRespuesta<String> result=new EntidadRespuesta<String>(HttpServletResponse.SC_BAD_REQUEST,
				"Error, favor revisar el token y la clave indicada",null,Tiempo.obtener());		
		
		//Validate if encryptedToken doesn´t exist
		if (!externalScheduler.listJobToken.containsKey(token)) {
			return result;
		}
		//get the job from the current list of schedule job
		JobTokenModel job = externalScheduler.listJobToken.get(token);	
		
		//decrypt public key with specific private key
		String text=cipher.decrypt(clavePrivada.getKey(), job.getText());		
		if(text==null) {
			return result;
		}
		result.setEstatus(HttpServletResponse.SC_OK);
		result.setDescripcion("Text has been successfully decrypted");
		result.setData(text);
		return result;
	}
}
