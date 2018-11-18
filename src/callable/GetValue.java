package callable;

import java.util.concurrent.Callable;

import capteur.CaptorMonitor;

/**
 * GetValue is a callable used by Display in order to get the new state of CaptorMonitor.
 * So it inherits from Callable and returns an Integer.
 * @author jgarnier
 */
public class GetValue implements Callable<Integer> {
	
	/**
	 * the captor monitor which contains the state that we are looking for
	 */
	private CaptorMonitor c;
	
	/**
	 * A simple constructor mapping the references to attributes.
	 * @param c
	 * @param obs
	 */
	public GetValue(CaptorMonitor c) {
		this.c = c;
	}

	/**
	 * call is a method inherited which will be called by the scheduler. It will return the state of CaptorMonitor
	 * according to its diffusion. 
	 */
	@Override
	public Integer call() throws Exception {
		return c.getValue();
	}

}
