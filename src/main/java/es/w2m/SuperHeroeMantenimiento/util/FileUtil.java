/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.util;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author Kevin Velásquez
 *
 */
public class FileUtil {
	public static void showFilesFromSpecificPath(String path) {
		// path=""; root folder
		// path="src/main/resources/"; specific folder path
		for (File file : new File(Paths.get(path).toAbsolutePath().toString()).listFiles()) {
			// show only files and not directories
			// if (file.isFile()) { }
			// To show all files and directories
			System.out.println(file.getName());
		}
	}
}
