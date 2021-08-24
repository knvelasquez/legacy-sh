package es.w2m.SuperHeroeMantenimiento;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;

@Configuration
@EnableScheduling
public class SpringConfig {

	/*@Scheduled(fixedDelay = 1000)
	public void scheduleFixedDelayTask() {
	    System.out.println(
	      "Fixed delay task - " + System.currentTimeMillis() / 1000);
	}
	
	@Scheduled(fixedRate = 1000)
	public void scheduleFixedRateTask() {
	    System.out.println(
	      "Fixed rate task - " + System.currentTimeMillis() / 1000);
	}
	
	@EnableAsync
	public class ScheduledFixedRateExample {
	    @Async
	    @Scheduled(fixedRate = 1000)
	    public void scheduleFixedRateTaskAsync() throws InterruptedException {
	        System.out.println(
	          "Fixed rate task async - " + System.currentTimeMillis() / 1000);
	        Thread.sleep(2000);
	    }

	}
	
	@Scheduled(fixedDelay = 1000, initialDelay = 1000)
	public void scheduleFixedRateWithInitialDelayTask() {
	 
	    long now = System.currentTimeMillis() / 1000;
	    System.out.println(
	      "Fixed rate task with one second initial delay - " + now);
	}*/
	
	//@Value("${prueba_scheduled}") 
	//public String hoursExpiredPassword;
	
	@Bean
	public String getConfigRefreshValue() {
	   //return configRepository.findOne(Constants.CONFIG_KEY_REFRESH_RATE).getConfigValue();
	   return "-";
	}
	
	
	//for every second: * * * ? * *
	//for every five second: */5 * * ? * *
	//@Scheduled(cron = "${prueba_scheduled}")
	//@Scheduled(cron = "#{@getConfigRefreshValue}")
	@Scheduled(cron = "*/5 * * ? * *")
	public void scheduleTaskUsingCronExpression() {
	 
	    long now = System.currentTimeMillis() / 1000;
	    System.out.println(
	      "schedule tasks using cron jobs - " + now);	    	   
	}
	public String getFixedDelay() {
        return "-";
	}
	
	String cronsExpressions = "*/5 * * ? * *";
		
	public void configureTasks(/*ScheduledTaskRegistrar scheduledTaskRegistrar*/) { 
		ScheduledTaskRegistrar scheduledTaskRegistrar=new ScheduledTaskRegistrar();
		// Split the cronExpression with pipe and for each expression add the same task.	
	    //Stream.of(StringUtils.split(cronsExpressions, "|")).forEach(cronExpression -> { 	
	         Runnable runnable = () -> System.out.println("Trigger task executed at " + new Date());		
	                      Trigger trigger = new Trigger() {		
	                        @Override	
	                        public Date nextExecutionTime(TriggerContext triggerContext) {		
	                            CronTrigger crontrigger = new CronTrigger(cronsExpressions);		
	                            return crontrigger.nextExecutionTime(triggerContext);		
	                        }		
	                   };		
	              scheduledTaskRegistrar.addTriggerTask(runnable, trigger);	
	    //});
	}
}
