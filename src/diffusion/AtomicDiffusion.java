package diffusion;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import canal.observer.ObsCaptorAsync;
import captor.impl.CaptorMonitor;
import captor.memento.CaptorOriginator;
import captor.memento.CaptorState;

/**
 * AtomicDiffusion sends all of captor state stored in a queue to each canal. It never sends a new captor state until all of 
 * observers would receive the last state. It inherits from Diffusion for the Strategy pattern. CaptorMonitor calls whatever
 * the diffusion execute method to update UI.
 */
public class AtomicDiffusion implements Diffusion {
	
	/**
	 * observers is a list of ObsCaptorAsync through AtomicDiffusion send update callables.
	 */
	private List<ObsCaptorAsync> observers;
	
	/**
	 * AtomicDiffusion only needs to know all observers in order to send update callables.
	 * Alternatively, it initializes all values such as lock to false or currentState to 0.
	 * @param observers : list of observers connected to displays
	 */
	public AtomicDiffusion() {
		this.observers = new ArrayList<>();
	}
	
	/**
	 * execute is called each time CaptorMonitor updates its state. According to the diffusion,
	 * it saves or not the new state of captorMonitor and notify or not displays of the new value.
	 * @param c : c is the ref of captor monitor which has updated its state 
	 */
	@Override
	public synchronized void execute(CaptorOriginator originator) {
		CaptorState state = originator.createMemento();
		// And we notice observers a new value is available
		for(ObsCaptorAsync o : observers) {
			CaptorOriginator captor = new CaptorMonitor();
			captor.restoreMemento(state);
			Future<Void> f = o.update(captor);
			try {
				f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}	
	}

	@Override
	public void attach(ObsCaptorAsync o) {
		this.observers.add(o);
	}

	@Override
	public void detach(ObsCaptorAsync o) {
		this.observers.add(o);
	}

	@Override
	public void notifyObs() {}
	
}
