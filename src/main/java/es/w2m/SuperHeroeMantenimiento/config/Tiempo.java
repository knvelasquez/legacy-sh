package es.w2m.SuperHeroeMantenimiento.config;
/**
 * 
 */

import java.sql.Timestamp;

/**
 * @author Kevin Velásquez
 *
 */
public class Tiempo {
	/**
	 * Metodo para obtener el tiempo actual
	 * 
	 * */
	public static Timestamp obtener() {
		return new Timestamp(System.currentTimeMillis());
	}
}
