package canal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import callable.GetValue;
import callable.Update;
import canal.observer.CaptorAsync;
import canal.observer.ObsCaptorAsync;
import captor.Captor;
import captor.impl.CaptorMonitor;
import client.observer.ObsCaptor;
import diffusion.Diffusion;
import scheduler.SchedulerMonitor;

/**
 * Canal is implementing all proxy interfaces. It is managing all interactions with the scheduler and the creation
 * of callables. 
 */
public class Canal implements ObsCaptorAsync, CaptorAsync {
	
	/**
	 * The captor monitor which contains the state
	 */
	private Captor captor;
	
	/**
	 * List of observers
	 */
	private List<ObsCaptor> observers;
	
	/**
	 * Scheduler which will manage callables that the canal creates.
	 */
	private SchedulerMonitor scheduler;
	
	/**
	 * Simple constructor mapping the references to attributes.
	 * @param captor
	 * @param scheduler
	 */
	public Canal(CaptorMonitor captor, SchedulerMonitor scheduler) {
		this.captor = captor;
		this.scheduler = scheduler;
		this.observers = new ArrayList<>();
	}

	/**
	 * update creates the update callable and call on scheduler scheduleUpdate to return a future to caller
	 */
	@Override
	public synchronized Future<Void> update(Captor c) {
		this.captor = c;
		Callable<Void> update = new Update(this);
		Long value = (long) (1000 * ThreadLocalRandom.current().nextDouble(0, 1) + 1);
		System.out.println("Valeur : " + value);
		return scheduler.scheduleUpdate(update, value, TimeUnit.MILLISECONDS);
	}

	/**
	 * getValue creates the getValue callable and call on scheduler scheduleUpdate to return a future to caller
	 */
	@Override
	public synchronized Future<Integer> getValue() {
		Callable<Integer> gv = new GetValue(captor);
		return scheduler.scheduleGetValue(gv, 200L, TimeUnit.MILLISECONDS);
	}

	/**
	 * Attach an ObsCaptor observer to the subject
	 */
	@Override
	public void attach(ObsCaptor o) {
		this.observers.add(o);
	}

	/**
	 * Detach an ObsCaptor observer of the subject
	 */
	@Override
	public void detach(ObsCaptor o) {
		this.observers.remove(o);
	}

	/**
	 * Notify all observers
	 */
	@Override
	public void notifyObs() {
		for(ObsCaptor o : observers)
			o.update(this);
	}

	/**
	 * Useless in our case because we use a custom update returning a Future.
	 */
	@Override
	public void update(Diffusion subject) {}

}
