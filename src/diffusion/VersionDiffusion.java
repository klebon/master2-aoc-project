package diffusion;

import java.util.ArrayList;
import java.util.List;

import canal.observer.ObsCaptorAsync;
import captor.impl.CaptorMonitor;
import captor.memento.CaptorOriginator;
import captor.memento.CaptorState;

/**
 * SequentialDiffusion call update on all mObservers as soon as possible. 
 * The result is a lawless diffusion of captor states to displays. 
 * It inherits from Diffusion for the Strategy pattern. 
 */
public class VersionDiffusion implements Diffusion {
	
	/**
	 * mObservers is the list of ObsCaptorAsync observers
	 */
	private List<ObsCaptorAsync> mObservers = new ArrayList<>();
		
	/**
	 * execute is called each time CaptorMonitor updates its state. 
	 * It calls update on all observers
	 * @param originator : originator is the reference of captor monitor which has updated its state 
	 */
	@Override
	public synchronized void execute(CaptorOriginator originator) {
		CaptorState cs = originator.createMemento();
		for(ObsCaptorAsync o : mObservers) {
			o.update(cs);
		}
	}
	
	/**
	 * Add a observer to our observer list
	 */
	@Override
	public void attach(ObsCaptorAsync o) {
		this.mObservers.add(o);
	}

	/**
	 * Remove a observer to our observer list
	 */
	@Override
	public void detach(ObsCaptorAsync o) {
		this.mObservers.remove(o);
	}

	/**
	 * Useless in our case of use. We notify observers in execute method.
	 */
	@Override
	public void notifyObs() {}

	
}
