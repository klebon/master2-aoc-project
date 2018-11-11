package canal;

import java.util.concurrent.Future;

/**
 * CaptorAsync is an interface used as proxy for Displays.
 * @author jgarnier
 */
public interface CaptorAsync {

	/**
	 * getValue will create the callable and returns its result as Future thank to scheduler.
	 * @return a Future which will contain the state of CaptorMonitor according to its diffusion
	 */
	public Future<Integer> getValue();

}
