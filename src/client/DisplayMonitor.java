package client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import canal.observer.CaptorAsync;
import client.observer.ObsCaptor;
import ihm.observer.ObsMonitor;
import ihm.observer.SubjectMonitor;

/**
 * DisplayMonitor encapsulates the monitor state and offers methods to get it or modify it.
 * Those methods are synchronized for thread safety.
 * DisplayMonitor implements also Subject interface in order to notify UI that its 
 * current state has been modified and so UI needs to updated.
 * DisplayMonitor implements also ObsCaptor, an Observer of CaptorAsync, to be notified by CaptorAsync
 * of all captor changes.
 */
public class DisplayMonitor implements ObsCaptor, SubjectMonitor {
	
	/**
	 * List of ObsMonitor observers that show the current value of the DisplayMonitor
	 */
	private List<ObsMonitor> observers = new ArrayList<>();
	
	/**
	 * The reference of the Future got from getValue()
	 */
	private Future<Integer> f;
	
	/**
	 * The current value of DisplayMonitor
	 */
	private Integer value;

	
	public synchronized Future<Integer> getF() {
		return f;
	}

	public synchronized void setF(Future<Integer> f) {
		this.f = f;
	}
	
	/**
	 * Set a new value to our DisplayMonitor and notify all observers that its state changed
	 * @param i 
	 */
	public synchronized void setValue(Integer i) {
		this.value = i;
		notifyObs();
	}

	@Override
	public synchronized Integer getState() {
		return this.value;
	}

	@Override
	public synchronized void attach(ObsMonitor o) {
		this.observers.add(o);
	}

	@Override
	public synchronized void detach(ObsMonitor o) {
		this.observers.remove(o);
	}
	
	@Override
	public synchronized void notifyObs() {
		for(ObsMonitor o : observers)
			o.update(this);
	}

	@Override
	public synchronized void update(CaptorAsync subject) {
		this.f = subject.getValue();				
	}
	
}
