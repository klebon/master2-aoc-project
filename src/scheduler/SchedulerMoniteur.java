package scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerMoniteur {
	
	private ScheduledExecutorService scheduler;
	
	public SchedulerMoniteur() {
		// on instancie
	}
	
	public synchronized Future<Integer> scheduleGetValue(Callable<Integer> c, Long delay, TimeUnit time) {
		return scheduler.schedule(c, delay, time);
	}
	
	public synchronized Future<Object> scheduleUpdate(Callable<Object> c, Long delay, TimeUnit time) {
		return scheduler.schedule(c, delay, time);
	}

}
