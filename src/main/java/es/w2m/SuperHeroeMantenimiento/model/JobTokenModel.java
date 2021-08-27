/**
 * 
 */
package es.w2m.SuperHeroeMantenimiento.model;

import java.util.concurrent.ScheduledFuture;

/**
 * @author Kevin Velásquez
 *
 */
public class JobTokenModel {

	@SuppressWarnings("rawtypes")
	private ScheduledFuture jobRunnable;
	private String text;
	private int secondScheduled;
	private long created;

	/**
	 * @param jobRunnable
	 * @param text
	 * @param key
	 */
	@SuppressWarnings("rawtypes")
	public JobTokenModel(ScheduledFuture jobRunnable, String text, int secondScheduled, long created) {
		this.jobRunnable = jobRunnable;
		this.text = text;
		this.secondScheduled = secondScheduled;
		this.created = created;
	}

	/**
	 * @return the schedule
	 */
	@SuppressWarnings("rawtypes")
	public ScheduledFuture getJobRunnable() {
		return jobRunnable;
	}

	/**
	 * @param jobRunnable the schedule to set
	 */
	@SuppressWarnings("rawtypes")
	public void setJobRunnable(ScheduledFuture jobRunnable) {
		this.jobRunnable = jobRunnable;
	}

	/**
	 * @return the value
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the value to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the secondScheduled
	 */
	public int getSecondScheduled() {
		return secondScheduled;
	}

	/**
	 * @param secondScheduled the secondScheduled to set
	 */
	public void setSecondScheduled(int secondScheduled) {
		this.secondScheduled = secondScheduled;
	}

	/**
	 * @return the created
	 */
	public long getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(long created) {
		this.created = created;
	}

}
