/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author Kevin Velásquez
 *
 */
public class PropUtil {
	
	public static int parseInt(String key, String filePath) {
		return Integer.parseInt(loadFromPath(filePath).getProperty(key));
	}

	public static Properties loadFromPath(String propertieFile) {
		FileInputStream fis = null;
		Properties prop = null;
		try {
			Path path = Paths.get("src/main/resources/".concat(propertieFile));
			System.out.println(String.format("Obtaining from %s", path.toAbsolutePath()));

			fis = new FileInputStream(path.toAbsolutePath().toString());
			prop = new Properties();
			prop.load(fis);
			System.out.println("Properties loaded susccesfull");
			
		} catch (FileNotFoundException exception) {
			System.out.println(exception.getStackTrace());
		} catch (IOException exception) {
			System.out.println(exception.getStackTrace());
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}
}
