package client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import canal.CaptorAsync;
import capteur.CaptorMonitor;
import ihm.ObsMonitor;
import ihm.SubjectMonitor;
import observer.Observer;
import observer.Subject;

//TODO: Commenter le code
/**
 * 
 * @author jgarnier
 *
 */
public class DisplayMonitor implements ObsCaptor, SubjectMonitor {
	
	private List<ObsMonitor> observers = new ArrayList<>();
	private Future<Integer> f;
	private Integer value;

	public synchronized Future<Integer> getF() {
		return f;
	}

	public synchronized void setF(Future<Integer> f) {
		this.f = f;
	}
	
	public synchronized void setValue(Integer i) {
		this.value = i;
		notifyObs();
	}

	@Override
	public synchronized Integer getState() {
		return this.value;
	}

	// SUBJECT METHODS 
	
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
