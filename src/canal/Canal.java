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
import captor.impl.CaptorMonitor;
import captor.memento.CaptorState;
import client.observer.ObsCaptor;
import diffusion.Diffusion;
import scheduler.SchedulerMonitor;

/**
 * Canal is implementing all proxy interfaces. It is managing all interactions with the mScheduler and the creation
 * of callables. 
 */
public class Canal implements ObsCaptorAsync, CaptorAsync {
	
	/**
	 * The CaptorState which contains the mCaptorState
	 */
	private CaptorState mCaptorState;
	
	/**
	 * List of observers
	 */
	private List<ObsCaptor> mObservers;
	
	/**
	 * Scheduler which will manage callables that the canal creates.
	 */
	private SchedulerMonitor mScheduler;
	
	/**
	 * Simple constructor mapping the references to attributes.
	 * @param mScheduler
	 */
	public Canal(SchedulerMonitor scheduler) {
		this.mScheduler = scheduler;
		this.mObservers = new ArrayList<>();
	}

	/**
	 * update creates the update callable and call scheduleUpdate to return a future to caller.
	 * It also keeps a reference on the state in order to return it when observers will ask for the state.
	 */
	@Override
	public synchronized Future<Void> update(CaptorState c) {
		this.mCaptorState = c;
		Callable<Void> update = new Update(this);
		Long value = (long) (1000 * ThreadLocalRandom.current().nextDouble(0, 1) + 1);
		return mScheduler.scheduleUpdate(update, value, TimeUnit.MILLISECONDS);
	}

	/**
	 * getValue creates the getValue callable and call scheduleUpdate to return a future to caller
	 */
	@Override
	public synchronized Future<Integer> getValue() {
		Callable<Integer> gv = new GetValue(mCaptorState);
		return mScheduler.scheduleGetValue(gv, 200L, TimeUnit.MILLISECONDS);
	}

	/**
	 * Attach an ObsCaptor observer to the subject
	 */
	@Override
	public void attach(ObsCaptor o) {
		this.mObservers.add(o);
	}

	/**
	 * Detach an ObsCaptor observer of the subject
	 */
	@Override
	public void detach(ObsCaptor o) {
		this.mObservers.remove(o);
	}

	/**
	 * Notify all mObservers
	 */
	@Override
	public void notifyObs() {
		for(ObsCaptor o : mObservers)
			o.update(this);
	}

	/**
	 * Useless in our case because we use a custom update returning a Future.
	 */
	@Override
	public void update(Diffusion subject) {}

}
