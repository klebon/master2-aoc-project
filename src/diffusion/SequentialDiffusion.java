 package diffusion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import canal.observer.ObsCaptorAsync;
import captor.memento.CaptorOriginator;
import captor.memento.CaptorState;

/**
 * SequentialDiffusion call update on all mObservers only when all of them have received the last one. 
 * The result is a sublist of captor states shown on UI. 
 * It inherits from Diffusion for the Strategy pattern. 
 */
public class SequentialDiffusion implements Diffusion {

	/**
	 * mLock is a boolean value used to enable or disable the process to call update on mObservers.
	 */
	private boolean mLock = false;
	
	/**
	 * mObservers is a list of ObsCaptorAsync
	 */
	private List<ObsCaptorAsync> mObservers = new ArrayList<>();
	
	/**
	 * Number of observers that have successfully received the update notification
	 */
	private int mCount;
	
	
	/**
	 * execute is called each time CaptorMonitor updates its state. 
	 * It will 
	 * @param originator : originator is the reference of captor monitor which has updated its state 
	 */
	@Override
	public synchronized void execute(CaptorOriginator originator) {
		// If the number of success receptions is equals to our observer list size
		// It means that we can again call update on them
		if(mCount == mObservers.size()) {
			mLock = false;
		}
		
		// If we can call update
		if(!mLock) {
			// We can not call update anymore until all observers received the update notification
			mLock = true;
			// And we reset the number of observers that have successfully received the update notification
			mCount = 0;
	
			CaptorState state = originator.createMemento();
			for(ObsCaptorAsync o : mObservers) {
				Future<Void> f = o.update(state);
				// Anonymous thread class that only call the blocking function get on the future and udpate mCount
				// once the observer have successfully received the update notification
				Thread t = new Thread() {
				    public void run() {
				    	try {
							f.get();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}
				    	mCount++;
				    }
				};
				t.start();
			}
		}
	}

	/**
	 * Add observer to our observer list
	 */
	@Override
	public void attach(ObsCaptorAsync o) {
		this.mObservers.add(o);
	}

	/**
	 * Remove observer to our observer list
	 */
	@Override
	public void detach(ObsCaptorAsync o) {
		this.mObservers.remove(o);
	}

	/**
	 * Useless in our case of use. We notify all observers in the execute method.
	 */
	@Override
	public void notifyObs() {}

}
