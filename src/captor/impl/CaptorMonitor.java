package captor.impl;

import java.util.ArrayList;
import java.util.List;

import captor.memento.CaptorOriginator;
import captor.memento.CaptorState;
import diffusion.Diffusion;
import ihm.observer.ObsMonitor;
import ihm.observer.SubjectMonitor;

/**
 * CaptorMonitor encapsulates the captor mState and offers methods to get it through CaptorOriginator.
 * Those methods are synchronized for thread safety.
 * CaptorMonitor implements also Subject interface in order to notify UI that its 
 * current mState has been modified and so UI needs to updated.
 */
public class CaptorMonitor implements SubjectMonitor, CaptorOriginator {
	
	/**
	 * The current diffusion used to diffuse the captor mState to canals
	 */
	private Diffusion mDiffusion;
	
	/**
	 * List of UI observers that CaptorMonitor have to update when its current state is modified
	 */
	private List<ObsMonitor> mObservers;
	
	/**
	 * Its current state
	 */
	private Integer mState;
	
	/**
	 * Initialize current mState to 0 and create an observers empty list 
	 */
	public CaptorMonitor() {
		this.mObservers = new ArrayList<>();
		this.mState = 0;
	}

	/**
	 * setDiffusion replaces the current mDiffusion to diffusion. 
	 * @param diffusion that will replace the current diffusion
	 */
	public synchronized void setDiffusion(Diffusion diffusion) {
		this.mDiffusion = diffusion;
	}

	/**
	 * setValue updates the current state, notifies mDiffusion and also mObservers of the changing. 
	 */
	public synchronized void setValue() {
		this.mState++;
		mDiffusion.execute(this);
		notifyObs();
	}

	/**
	 * getState is a method inherited from Subject which returns the current mState
	 * @return current mState of the monitor
	 */
	@Override
	public synchronized Integer getState() {
		return mState;
	}

	/**
	 * attach adds an observer in the list of mObservers which need to be notified for each changing
	 * @param o : observer which needs to be notified
	 */
	@Override
	public void attach(ObsMonitor o) {
		mObservers.add(o);		
	}

	/**
	 * detach remove an observer of the list of mObservers which need to be notified for each changing
	 * @param o : observer which doesn't need anymore to be notified
	 */
	@Override
	public void detach(ObsMonitor o) {
		mObservers.remove(o);		
	}

	/**
	 * notifyObs notifies all mObservers contained in the list of mObservers that a changing happened.
	 */
	@Override
	public void notifyObs() {
		for(ObsMonitor o : mObservers)
			o.update(this);
	}

	/**
	 * createMemento returns a CaptorState which is encapsulating the state of CaptorMonitor
	 */
	@Override
	public CaptorState createMemento() {
		return new CaptorState(this.mState);
	}

	/**
	 * restoreMemento set the state of CaptorState to CaptorMonitor 
	 */
	@Override
	public void restoreMemento(CaptorState state) {
		this.mState = state.getState();
	}
}
