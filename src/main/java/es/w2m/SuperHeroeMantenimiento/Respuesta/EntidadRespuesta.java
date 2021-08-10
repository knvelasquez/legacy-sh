/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.Respuesta;

import java.sql.Timestamp;
import com.google.gson.GsonBuilder;

/**
 * @author Kevin Velásquez
 *
 */
public class EntidadRespuesta<E> {
	private Timestamp tiempo;
	private int estatus;
	private String descripcion;
	private E data;

	/**
	 * @param tiempo
	 * @param estatus
	 * @param descripcion
	 * @param data
	 */
	public EntidadRespuesta(int estatus, String descripcion, E data, Timestamp tiempo) {
		this.estatus = estatus;
		this.descripcion = descripcion;
		this.data = data;
		this.tiempo = tiempo;
	}

	public EntidadRespuesta() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the tiempo
	 */
	public String getTiempo() {
		return tiempo.toString();
	}

	/**
	 * @param tiempo the tiempo to set
	 */
	public void setTiempo(Timestamp tiempo) {
		this.tiempo = tiempo;
	}

	/**
	 * @return the codigo
	 */
	public int getEstatus() {
		return estatus;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setEstatus(int codigo) {
		this.estatus = codigo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the data
	 */
	public E getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(E data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}

}
