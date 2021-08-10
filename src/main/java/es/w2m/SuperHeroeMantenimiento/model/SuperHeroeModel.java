package es.w2m.SuperHeroeMantenimiento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import es.w2m.SuperHeroeMantenimiento.enumerado.Color;
import es.w2m.SuperHeroeMantenimiento.enumerado.Habilidad;

/**
 * @author Kevin Velásquez
 *
 */
@Entity(name = "SuperHeroe")
@Table(name = "SUPER_HEROE")
public class SuperHeroeModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "identidad_secreta", nullable = false)
	private String identidadSecreta;

	@Column(name = "lugar_residencia", nullable = true)
	private String lugarResidencia;

	@Column(name = "super_poder", nullable = true)
	private String superPoder;

	@Column(name = "logo", nullable = true)
	private String logo;

	@Column(name = "color", nullable = true)
	//private Color color;
	private String color;

	@Column(name = "archi_enemigo", nullable = true)
	private String archiEnemigo;

	public SuperHeroeModel() {
		
	}

	/**
	 * @param id
	 * @param nombre
	 * @param identidadSecreta
	 * @param lugarResidencia
	 * @param superpoderes
	 * @param logo
	 * @param color
	 * @param archiEnemigo
	 */
	public SuperHeroeModel(int id, String nombre, String identidadSecreta, String lugarResidencia,
			Habilidad superpoderes, String logo, Color color, String archiEnemigo) {
		this.id = id;
		this.nombre = nombre;
		/*this.identidadSecreta = identidadSecreta;
		this.lugarResidencia = lugarResidencia;
		this.superpoderes = superpoderes;
		this.logo = logo;
		this.color = color;
		this.archiEnemigo = archiEnemigo;*/
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
	 * @return the identidadSecreta
	 */
	public String getIdentidadSecreta() {
		return identidadSecreta;
	}

	/**
	 * @param identidadSecreta the identidadSecreta to set
	 */
	public void setIdentidadSecreta(String identidadSecreta) {
		this.identidadSecreta = identidadSecreta;
	}

	/**
	 * @return the lugarResidencia
	 */
	public String getLugarResidencia() {
		return lugarResidencia;
	}

	/**
	 * @param lugarResidencia the lugarResidencia to set
	 */
	public void setLugarResidencia(String lugarResidencia) {
		this.lugarResidencia = lugarResidencia;
	}

	/**
	 * @return the superPoder
	 */
	public String getSuperPoder() {
		return superPoder;
	}

	/**
	 * @param superPoder the superPoder to set
	 */
	public void setSuperPoder(String superPoder) {
		this.superPoder = superPoder;
	}

	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}	

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the archiEnemigo
	 */
	public String getArchiEnemigo() {
		return archiEnemigo;
	}

	/**
	 * @param archiEnemigo the archiEnemigo to set
	 */
	public void setArchiEnemigo(String archiEnemigo) {
		this.archiEnemigo = archiEnemigo;
	}

}