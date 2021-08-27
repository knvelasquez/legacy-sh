/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import es.w2m.SuperHeroeMantenimiento.model.JobTokenModel;
import es.w2m.SuperHeroeMantenimiento.util.DateUtil;
import es.w2m.SuperHeroeMantenimiento.util.PropUtil;

/**
 * @author Kevin Velasquez
 *
 */
@Service
public class ExternalScheduler implements SchedulingConfigurer {

	private ScheduledTaskRegistrar scheduledTaskRegistrar;

	public Map<String, JobTokenModel> listJobToken = new HashMap<String, JobTokenModel>();

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

	@SuppressWarnings({ "rawtypes" })
	public void addJob(String jobName, String text) {
		//check if exist ScheduleJob
		if (this.listJobToken.containsKey(jobName)) {
			return;
		}
				
		int seconds = PropUtil.parseInt("second_scheduled","application.properties");
		ScheduledFuture jobRunnable = addRunnableJobWithSecondTriggerTime(() -> methodToExec(jobName),seconds);
		this.configureTasks(scheduledTaskRegistrar);
		// set in the quee
		listJobToken.put(jobName, new JobTokenModel(jobRunnable,text,seconds,DateUtil.getCurrentLongDate()));			
		//
		System.out.println(String.format("ScheduledJob %s successfully added in %s seconds to work queue",jobName, seconds));		
	}
	
	@SuppressWarnings("rawtypes")
	public void removeJob(String name) {
		if (!this.listJobToken.containsKey(name)) {
			return;
		}
		ScheduledFuture schedule = this.listJobToken.get(name).getJobRunnable();
		System.out.println("Obtaining from work queue...");
		schedule.cancel(true);
		this.listJobToken.remove(name);
		System.out.println("Cancelling SheduleJob...");
	}

	public void methodToExec(String name) {
		long now = System.currentTimeMillis() / 1000;
		System.out.println(String.format("Time´s up %s", now));
		listJobToken.remove(name);
		System.out.println(String.format("Removing ScheduleJob %s", name));
		this.removeJob(name);
		System.out.println(String.format("ScheduleJob %s removed successfully from work queue", name));
	}
}
