package capteur;

import java.util.ArrayList;
import java.util.List;

import client.ObsCaptor;
import diffusion.Diffusion;
import observer.Subject;
import observer.Observer;

/**
 * CaptorMonitor encapsulates the captor state and offers methods to get it or modify it.
 * Those methods are synchronized for thread safety.
 * CaptorMonitor implements also Subject interface in order to notify UI that its 
 * current state has been modified and so UI needs to updated.
 * @author jgarnier
 */
public class CaptorMonitor implements Subject {
	
	/**
	 * The current diffusion used to diffuse the captor state to canals
	 */
	private Diffusion diffusion;
	
	/**
	 * List of observers that CaptorMonitor have to update when its current state is modified
	 */
	private List<Observer> observers;
	
	/**
	 * Its current state
	 */
	private Integer state;
	
	/**
	 * Initialize current state to 0 and create an observers empty list 
	 */
	public CaptorMonitor() {
		this.state = 0;
		this.observers = new ArrayList<>();
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
	public synchronized Integer getValue(ObsCaptor obs) {
		return diffusion.getValue(obs);
	}

	/**
	 * setValue updates the current state, notifies diffusion and also observers of the changing. 
	 */
	public synchronized void setValue() {
		state++;
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
	public void attach(Observer o) {
		observers.add(o);		
	}

	/**
	 * detach remove an observer of the list of observers which need to be notified for each changing
	 * @param o : observer which doesn't need anymore to be notified
	 */
	@Override
	public void detach(Observer o) {
		observers.remove(o);		
	}

	/**
	 * notifyObs notifies all observers contained in the list of observers that a changing happened.
	 */
	@Override
	public void notifyObs() {
		for(Observer o : observers)
			o.update();
	}
	
}