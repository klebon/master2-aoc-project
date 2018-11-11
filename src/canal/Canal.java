package canal;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import callable.GetValue;
import callable.Update;
import capteur.CaptorMonitor;
import client.ObsCaptor;
import scheduler.SchedulerMonitor;

/**
 * Canal is implementing all proxy interfaces. It is managing all interactions with the scheduler and the creation
 * of callables. 
 * @author jgarnier
 */
public class Canal implements ObsCaptorAsync, CaptorAsync {
	
	/**
	 * The captor monitor which contains the current state
	 */
	private CaptorMonitor captor;
	
	/**
	 * The captor monitor observer that update callable will notify  
	 */
	private ObsCaptor obs;
	
	/**
	 * Scheduler which will manage callables that the canal creates.
	 */
	private SchedulerMonitor scheduler;
	
	/**
	 * Simple constructor mapping the references to attributes.
	 * @param captor
	 * @param obs
	 * @param scheduler
	 */
	public Canal(CaptorMonitor captor, ObsCaptor obs, SchedulerMonitor scheduler) {
		this.captor = captor;
		this.obs = obs;
		this.scheduler = scheduler;
	}

	
	// FIXME: Retirer la référence CaptorMonitor c.
	/**
	 * update creates the update callable and call on scheduler scheduleUpdate to return a future to caller
	 */
	@Override
	public Future<Object> update(CaptorMonitor c) {
		Callable<Object> update = new Update(obs);
		return scheduler.scheduleUpdate(update, 500L, TimeUnit.MILLISECONDS);
	}

	/**
	 * getValue creates the getValue callable and call on scheduler scheduleUpdate to return a future to caller
	 */
	@Override
	public Future<Integer> getValue() {
		Callable<Integer> gv = new GetValue(captor, obs);
		return scheduler.scheduleGetValue(gv, 500L, TimeUnit.MILLISECONDS);
	}

}
