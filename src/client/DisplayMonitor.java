package client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
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
	 * List of ObsMonitor observers that show the current mValue of the DisplayMonitor
	 */
	private List<ObsMonitor> mObservers = new ArrayList<>();
	
	/**
	 * The current value of DisplayMonitor
	 */
	private Integer mValue;

	/**
	 * Add an ObsMonitor to mObservers list
	 */
	@Override
	public synchronized void attach(ObsMonitor o) {
		this.mObservers.add(o);
	}

	/**
	 * Remove an ObsMonitor to mObservers list
	 */
	@Override
	public synchronized void detach(ObsMonitor o) {
		this.mObservers.remove(o);
	}
	
	/**
	 * Notify all observers
	 */
	@Override
	public synchronized void notifyObs() {
		for(ObsMonitor o : mObservers)
			o.update(this);
	}
	

	/**
	 * Notify DisplayMonitor that a new value was released. DisplayMonitor uses CaptorAsync proxy to get this new value.
	 * It is waiting until the future gets the new value and it will notify all observers that its state changed
	 * @param subject is the proxy 
	 */
	@Override
	public synchronized void update(CaptorAsync subject) {
		Future<Integer> f = subject.getValue();
		try {
			this.mValue = f.get();
			notifyObs();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the current state of DisplayMonitor
	 */
	@Override
	public synchronized Integer getState() {
		return this.mValue;
	}
	
}
