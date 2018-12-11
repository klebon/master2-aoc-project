package scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * SchedulerMonitor encapsulates the scheduler and manages all schedule actions with the scheduler
 */
public class SchedulerMonitor {
	
	/**
	 * Encapsulated scheduler
	 */
	private final ScheduledExecutorService scheduler;
	
	/**
	 * Creates the scheduler
	 */
	public SchedulerMonitor() {
		this.scheduler = Executors.newScheduledThreadPool(Integer.MAX_VALUE);
	}
	
	/**
	 * scheduleGetValue is like schedule method of ScheduledExecutorService but it is synchronized
	 * @param c
	 * @param delay
	 * @param time
	 * @return 
	 */
	public synchronized Future<Integer> scheduleGetValue(Callable<Integer> c, Long delay, TimeUnit time) {
		return scheduler.schedule(c, delay, time);
	}
	
	/**
	 * scheduleGetValue is like schedule method of ScheduledExecutorService but it is synchronized
	 * @param c
	 * @param delay
	 * @param time
	 * @return 
	 */
	public synchronized Future<Void> scheduleUpdate(Callable<Void> c, Long delay, TimeUnit time) {
		return scheduler.schedule(c, delay, time);
	}

}
