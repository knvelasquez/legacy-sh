/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Solicitud;

import com.google.gson.GsonBuilder;

/**
 * @author Kevin Velásquez
 *
 */
public class SuperHeroeSolicitud {
	private int identificacion;
	private String nombre;
	private String identidadSecreta;
	private String lugarResidencia;
	private String superPoder;
	private String logo;
	private String color;
	private String archiEnemigo;

	/**
	 * @return the id
	 */
	public int getIdentificacion() {
		return identificacion;
	}

	/**
	 * @param identificacion the id to set
	 */
	public void setIdentificacion(int identificacion) {
		this.identificacion = identificacion;
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

	@Override
	public String toString() {
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}

}
