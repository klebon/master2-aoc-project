package callable;

import java.util.concurrent.Callable;

import canal.CaptorAsync;

/**
 * Update is a callable used by CaptorMonitor in order to notify Displays that a new state was generated.
 * So it inherits from Callable and returns an Object because the returning value is not interesting at 
 * all in this case of use.
 * @author jgarnier
 */
public class Update implements Callable<Object> {

	/**
	 * obs captor which will be noticed about the captor monitor update
	 */
	private CaptorAsync proxy;
	
	/**
	 * A simple constructor mapping the reference to attribute.
	 * @param proxy
	 */
	public Update(CaptorAsync proxy) {
		this.proxy = proxy;
	}
	
	/**
	 * call is a method inherited which will be called by the scheduler. 
	 * It will notify obs that CaptorMonitor updated.
	 */
	@Override
	public Object call() throws Exception {
		proxy.notifyObs();
		return null;
	}

}
