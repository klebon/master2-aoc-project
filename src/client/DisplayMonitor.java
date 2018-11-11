package client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import canal.CaptorAsync;
import capteur.CaptorMonitor;
import observer.Observer;
import observer.Subject;

//TODO: Commenter le code
/**
 * 
 * @author jgarnier
 *
 */
public class DisplayMonitor implements ObsCaptor, Subject {
	
	private List<Observer> observers = new ArrayList<>();
	private CaptorAsync canal;
	private Integer value;
	private Future<Integer> f;
	
	@Override
	public synchronized void update(CaptorMonitor c) {
		this.f = canal.getValue();
	}

	public synchronized void setCanal(CaptorAsync canal) {
		this.canal = canal;
	}

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
	public void attach(Observer o) {
		observers.add(o);
	}

	@Override
	public void detach(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObs() {
		for(Observer o : observers)
			o.update();
	}

	@Override
	public synchronized Integer getState() {
		return this.value;
	}
	
}
