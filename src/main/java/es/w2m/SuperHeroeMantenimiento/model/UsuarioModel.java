/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Kevin Velásquez
 *
 */
@Entity(name = "Usuario")
@Table(name = "USUARIO")
public class UsuarioModel {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idusuario", nullable = false)
	private int id;
	
	@Column(name = "usuario", nullable = false)
	private String usuario;
	
	@Column(name = "contrasenia", nullable = false)
	private String contrasenia;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "apellido", nullable = false)
	private String apellido;
	
	//@ManyToMany()
	@OneToMany(fetch = FetchType.EAGER)
	private List<PrivilegioModel> privilegio;

	/**
	 * 
	 */
	public UsuarioModel() {
	}

	/**
	 * @param usuario
	 */
	public UsuarioModel(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @param usuario
	 * @param contrasenia
	 */
	public UsuarioModel(String usuario, String contrasenia) {
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}

	/**
	 * @param usuario
	 * @param contrasenia
	 * @param prvilegios
	 */
	public UsuarioModel(String usuario, String contrasenia, List<PrivilegioModel> privilegios) {
		this.usuario = usuario;
		this.contrasenia = contrasenia;
		this.privilegio = privilegios;
	}
	
	/**
	 * @param usuario
	 * @param privilegios
	 */
	public UsuarioModel(String usuario, List<PrivilegioModel> privilegios) {
		this.usuario = usuario;
		this.privilegio = privilegios;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * @param contrasenia the contrasenia to set
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}	

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @return the privilegio
	 */
	public List<PrivilegioModel> getPrivilegio() {
		return privilegio;
	}

	/**
	 * @param privilegio the privilegio to set
	 */
	public void setPrivilegio(List<PrivilegioModel> privilegio) {
		this.privilegio = privilegio;
	}	
	
}
