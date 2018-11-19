package scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * TODO: Commenter le code
 */
public class SchedulerMonitor {
	
	private final ScheduledExecutorService scheduler;
	
	public SchedulerMonitor() {
		this.scheduler = Executors.newScheduledThreadPool(1);
	}
	
	public synchronized Future<Integer> scheduleGetValue(Callable<Integer> c, Long delay, TimeUnit time) {
		return scheduler.schedule(c, delay, time);
	}
	
	public synchronized Future<Object> scheduleUpdate(Callable<Object> c, Long delay, TimeUnit time) {
		return scheduler.schedule(c, delay, time);
	}

}
