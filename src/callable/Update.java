package callable;

import java.util.concurrent.Callable;

import canal.observer.CaptorAsync;

/**
 * Update is a callable used by CaptorMonitor in order to notify Displays that a new state was generated.
 * So it inherits from Callable and returns an Object because the returning value is not interesting at 
 * all in this case of use.
 */
public class Update implements Callable<Void> {

	/**
	 * CaptorAsync which will be noticed about the captor monitor update
	 */
	private CaptorAsync mCaptorAsync;
	
	/**
	 * A simple constructor mapping the reference to attribute.
	 * @param mCaptorAsync
	 */
	public Update(CaptorAsync proxy) {
		this.mCaptorAsync = proxy;
	}
	
	
	/**
	 * call is a method inherited which will be called by the scheduler. 
	 * It will notify observers of mCaptorAsync that CaptorMonitor updated.
	 */
	@Override
	public Void call() throws Exception {
		mCaptorAsync.notifyObs();
		return null;
	}

}
