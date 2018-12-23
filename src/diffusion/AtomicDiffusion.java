package diffusion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import canal.observer.ObsCaptorAsync;
import captor.memento.CaptorOriginator;
import captor.memento.CaptorState;

/**
 * AtomicDiffusion notify all observers that a new value is available once execute method is called. 
 * It will wait that observer have successfully received the value before sending the value to the next one.
 * It inherits from Diffusion for the Strategy pattern.
 */
public class AtomicDiffusion implements Diffusion {
	
	/**
	 * mObservers is a list of ObsCaptorAsync.
	 */
	private List<ObsCaptorAsync> mObservers = new ArrayList<>();;
	
	/**
	 * execute is called each time CaptorMonitor updates its state. 
	 * It will notice and wait that observer have successfully received the value before sending 
	 * the value to the next one.
	 * @param originator : originator is the reference of captor monitor which has updated its state
	 */
	@Override
	public synchronized void execute(CaptorOriginator originator) {
		CaptorState state = originator.createMemento();
		for(ObsCaptorAsync o : mObservers) {
			Future<Void> f = o.update(state);
			try {
				f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}	
	}

	/**
	 * Add an ObsCaptorAsync to mObservers list
	 */
	@Override
	public void attach(ObsCaptorAsync o) {
		this.mObservers.add(o);
	}

	/**
	 * Remove an ObsCaptorAsync to mObservers list
	 */
	@Override
	public void detach(ObsCaptorAsync o) {
		this.mObservers.add(o);
	}

	/**
	 * Useless in our case of use. We notify observers in execute method.
	 */
	@Override
	public void notifyObs() {}
	
}
