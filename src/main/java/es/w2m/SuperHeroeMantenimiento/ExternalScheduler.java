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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
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

	public void showFilesFromSpecificPath(String path) {
		// path=""; root folder
		// path="src/main/resources/"; specific folder path
		for (File file : new File(Paths.get(path).toAbsolutePath().toString()).listFiles()) {
			// show only files and not directories
			// if (file.isFile()) { }
			// To show all files and directories
			System.out.println(file.getName());
		}
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		if (scheduledTaskRegistrar == null) {
			scheduledTaskRegistrar = taskRegistrar;
		}
		if (taskRegistrar.getScheduler() == null) {
			// taskRegistrar.setScheduler(poolScheduler());
		}
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	public void addCronTriggerJobWithRunnableTaskAndLambdaExpression(String name) {
		CronTrigger cron = new CronTrigger("*/30 * * ? * *", TimeZone.getDefault());

		// Runnable task
		Runnable task = new Runnable() {
			public void run() {
				System.out.println("schedule tasks using Runnable method");
			}
		};

		// Example CronTrigger with Runnable Task
		ScheduledFuture scheduleRunnable = scheduledTaskRegistrar.getScheduler().schedule(task, cron);

		// Example CronTrigger with lambda expression
		ScheduledFuture scheduleLambda = scheduledTaskRegistrar.getScheduler().schedule(() -> methodToExec(name), cron);
	}

	@SuppressWarnings("rawtypes")
	public ScheduledFuture addRunnableJobWithSecondTriggerTime(Runnable task, int second) {
		// Set the trigger expression
		Trigger trigger = new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				Calendar nextExecutionTime = new GregorianCalendar();
				Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
				nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
				nextExecutionTime.add(Calendar.SECOND, second);
				return nextExecutionTime.getTime();
			}
		};

		// set the schedule
		return this.scheduledTaskRegistrar.getScheduler().schedule(task, trigger);
	}

	@SuppressWarnings("rawtypes")
	public void removeJob(String name) {
		if (!this.futureMap.containsKey(name)) {
			return;
		}
		ScheduledFuture schedule = this.futureMap.get(name);
		System.out.println("Obtaining from work queue...");

		schedule.cancel(true);
		this.futureMap.remove(name);
		System.out.println("Cancelling SheduleJob...");
	}

	@SuppressWarnings("rawtypes")
	public void addJob(String name) {
		if (this.futureMap.containsKey(name)) {
			return;
		}
		try {
			// set in the quee
			list.put(name, null);
			Properties prop = this.loadProp("application.properties");

			int secondScheduled = Integer.parseInt(prop.getProperty("second_scheduled"));
			//System.out.println(String.format("param secondScheduled %s ", secondScheduled));

			ScheduledFuture schedule = addRunnableJobWithSecondTriggerTime(() -> methodToExec(name), secondScheduled);
			//System.out.println(String.format("Setting %s schedule job",name));

			this.configureTasks(scheduledTaskRegistrar);
			this.futureMap.put(name, schedule);

			System.out.println(String.format("ScheduledJob %s successfully added in %s seconds to work queue", name,secondScheduled));

		} catch (IOException e) {
			System.out.println(e.getMessage() + " " + e.getStackTrace());
		}
	}

	public void methodToExec(String name) {
		long now = System.currentTimeMillis() / 1000;
		System.out.println(String.format("Time´s up %s",now));
		list.remove(name);
		System.out.println(String.format("Removing ScheduleJob %s", name));
		this.removeJob(name);
		System.out.println(String.format("ScheduleJob %s removed successfully from work queue",name));		
	}

	public Properties loadProp(String propertieFile) throws IOException {
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
			fis.close();
		}
		return prop;
	}
}
