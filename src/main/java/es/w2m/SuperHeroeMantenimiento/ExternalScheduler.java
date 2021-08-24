/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

/**
 * @author Kevin Velasquez
 *
 */
@Service
public class ExternalScheduler implements SchedulingConfigurer {

	public Map<String, String> list = new HashMap<String, String>();

	// @Value("${cron_scheduled}")
	public String cron_scheduled;

	private ScheduledTaskRegistrar scheduledTaskRegistrar;

	@SuppressWarnings("rawtypes")
	private Map<String, ScheduledFuture> futureMap = new HashMap<String, ScheduledFuture>();

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		if (scheduledTaskRegistrar == null) {
			scheduledTaskRegistrar = taskRegistrar;
		}
		if (taskRegistrar.getScheduler() == null) {
			// taskRegistrar.setScheduler(poolScheduler());
		}
	}

	final Runnable task = new Runnable() {
	    public void run() {
	    	System.out.println("methodToExec");
			System.out.println(String.format("removing '%s' from list",2));			
			System.out.println("ok...");
	    }
	};
	
	@SuppressWarnings("rawtypes")
	public void addJob(String name) {
		if (this.futureMap.containsKey(name)) {
			return;
		}
		try {
			// set in the quee
			list.put(name, null);
			Properties prop = readPropertiesFile("application.properties");
			System.out.println("Setting cron "+prop.getProperty("cron_scheduled"));
			
			CronTrigger cron = new CronTrigger(prop.getProperty("cron_scheduled"), TimeZone.getDefault());
			System.out.println("cron established ok");
					
			//ScheduledFuture schedule = scheduledTaskRegistrar.getScheduler().schedule(task, cron);
			ScheduledFuture schedule = scheduledTaskRegistrar.getScheduler().schedule(() -> methodToExec(name), cron);

			System.out.println("schedule ok");
			this.configureTasks(scheduledTaskRegistrar);
			this.futureMap.put(name, schedule);
			
			System.out.println("ok");

		} catch (IOException e) {
			System.out.println(e.getMessage() + " " + e.getStackTrace());
		}
	}

	@SuppressWarnings("rawtypes")
	public void removeJob(String name) {
		if (!this.futureMap.containsKey(name)) {
			return;
		}
		ScheduledFuture schedule = this.futureMap.get(name);
		schedule.cancel(true);
		this.futureMap.remove(name);
	}

	public void methodToExec(String name) {
		System.out.println("methodToExec");
		list.remove(name);
		System.out.println(String.format("removing '%s' from list",name));
		this.removeJob(name);
		System.out.println("ok...");
		/*
		 * try { Properties prop = readPropertiesFile("application.properties");
		 * System.out.println("Hello There" + prop.getProperty("cron_scheduled"));
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

	public static Properties readPropertiesFile(String fileName) throws IOException {
		Path currentRelativePath = Paths.get("");		
		String s = currentRelativePath.toAbsolutePath().toString();

		File folder = new File(s);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			// if (file.isFile()) { }
			//System.out.println(file.getName());		
		}
		
		Path currentSrcRelativePath = Paths.get("src/main/resources/");
		s = currentSrcRelativePath.toAbsolutePath().toString();

		folder = new File(s);
		listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			//System.out.println(file.getName());		
		}
		
		FileInputStream fis = null;
		Properties prop = null;
		try {
			Path path = Paths.get("src/main/resources/".concat(fileName));
			System.out.println(String.format("Reading file %s", path.toAbsolutePath()));
			fis = new FileInputStream(path.toAbsolutePath().toString());
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			fis.close();
		}
		return prop;
	}
}
