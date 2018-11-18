package canal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import callable.GetValue;
import callable.Update;
import capteur.CaptorMonitor;
import client.ObsCaptor;
import diffusion.Diffusion;
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
	private List<ObsCaptor> observers;
	
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
	public Canal(CaptorMonitor captor, SchedulerMonitor scheduler) {
		this.captor = captor;
		this.scheduler = scheduler;
		this.observers = new ArrayList<>();
	}

	
	// FIXME: Retirer la référence CaptorMonitor c.
	/**
	 * update creates the update callable and call on scheduler scheduleUpdate to return a future to caller
	 */
	@Override
	public Future<Object> update(CaptorMonitor c) {
		Callable<Object> update = new Update(this);
		return scheduler.scheduleUpdate(update, 500L, TimeUnit.MILLISECONDS);
	}

	/**
	 * getValue creates the getValue callable and call on scheduler scheduleUpdate to return a future to caller
	 */
	@Override
	public Future<Integer> getValue() {
		Callable<Integer> gv = new GetValue(captor);
		return scheduler.scheduleGetValue(gv, 300L, TimeUnit.MILLISECONDS);
	}


	@Override
	public void attach(ObsCaptor o) {
		this.observers.add(o);
	}


	@Override
	public void detach(ObsCaptor o) {
		this.observers.remove(o);
	}

	@Override
	public void notifyObs() {
		for(ObsCaptor o : observers)
			o.update(this);
	}


	@Override
	public void update(Diffusion subject) {
	
	}

}
