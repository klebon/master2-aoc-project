package captor.impl;

import java.util.ArrayList;
import java.util.List;

import captor.memento.CaptorOriginator;
import captor.memento.CaptorState;
import diffusion.Diffusion;
import ihm.observer.ObsMonitor;
import ihm.observer.SubjectMonitor;

/**
 * CaptorMonitor encapsulates the captor state and offers methods to get it or modify it.
 * Those methods are synchronized for thread safety.
 * CaptorMonitor implements also Subject interface in order to notify UI that its 
 * current state has been modified and so UI needs to updated.
 */
public class CaptorMonitor implements SubjectMonitor, CaptorOriginator {
	
	/**
	 * The current diffusion used to diffuse the captor state to canals
	 */
	private Diffusion diffusion;
	
	/**
	 * List of UI observers that CaptorMonitor have to update when its current state is modified
	 */
	private List<ObsMonitor> observers;
	
	/**
	 * Its current state
	 */
	private Integer state;
	
	/**
	 * Initialize current state to 0 and create an observers empty list 
	 */
	public CaptorMonitor() {
		this.observers = new ArrayList<>();
		this.state = 0;
	}

	/**
	 * setDiffusion replaces the current diffusion to diffusion. 
	 * @param diffusion
	 */
	public synchronized void setDiffusion(Diffusion diffusion) {
		this.diffusion = diffusion;
	}
	
	/**
	 * getValue is a getter returning the state
	 * @param obs : obs reference asking for the value
	 * @return the state according to the current diffusion
	 */
	@Override
	public synchronized Integer getValue() {
		return this.state;
	}

	/**
	 * setValue updates the current state, notifies diffusion and also observers of the changing. 
	 */
	public synchronized void setValue() {
		this.state++;
		diffusion.execute(this);
		notifyObs();
	}

	/**
	 * getState is a method inherited from Subject which returns the current state
	 * @return current state of the monitor
	 */
	@Override
	public synchronized Integer getState() {
		return state;
	}

	/**
	 * attach adds an observer in the list of observers which need to be notified for each changing
	 * @param o : observer which needs to be notified
	 */
	@Override
	public void attach(ObsMonitor o) {
		observers.add(o);		
	}

	/**
	 * detach remove an observer of the list of observers which need to be notified for each changing
	 * @param o : observer which doesn't need anymore to be notified
	 */
	@Override
	public void detach(ObsMonitor o) {
		observers.remove(o);		
	}

	/**
	 * notifyObs notifies all observers contained in the list of observers that a changing happened.
	 */
	@Override
	public void notifyObs() {
		for(ObsMonitor o : observers)
			o.update(this);
	}

	@Override
	public CaptorState createMemento() {
		return new CaptorState(this.state);
	}

	@Override
	public void restoreMemento(CaptorState state) {
		this.state = state.getState();
	}
}
