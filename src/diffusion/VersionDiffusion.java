package diffusion;

import java.util.ArrayList;
import java.util.List;

import canal.observer.ObsCaptorAsync;
import captor.impl.CaptorMonitor;
import captor.memento.CaptorOriginator;
import captor.memento.CaptorState;

public class VersionDiffusion implements Diffusion {
	
	private List<ObsCaptorAsync> observers;
		
	/**
	 * SequentialDiffusion only needs to know all observers in order to send update callables.
	 * Alternatively, it initializes all values such as lock to false or currentState to 0.
	 * @param observers : list of observers connected to displays
	 */
	public VersionDiffusion() {
		this.observers = new ArrayList<>();
	}
	
	/**
	 * execute is called each time CaptorMonitor updates its state. According to the diffusion,
	 * it saves or not the new state of captorMonitor and notify or not displays of the new value.
	 * @param c : c is the ref of captor monitor which has updated its state 
	 */
	@Override
	public synchronized void execute(CaptorOriginator originator) {
		// In order to set a new value and call update to each Canal again
		CaptorState cs = originator.createMemento();
		for(ObsCaptorAsync o : observers) {
			CaptorOriginator captor = new CaptorMonitor();
			captor.restoreMemento(cs);
			o.update(captor);
		}
	}

	/**
	 * getValue returns the available state according to the diffusion.
	 * It also keeps a track on obs in order to know if all ObsCapteurs have received the available state.
	 * @param obs : obs asking for the available state.
	 * @return currentState
	 */

	@Override
	public void attach(ObsCaptorAsync o) {
		this.observers.add(o);
	}

	@Override
	public void detach(ObsCaptorAsync o) {
		this.observers.remove(o);
	}

	@Override
	public void notifyObs() {}

	
}
