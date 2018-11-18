package canal;

import java.util.concurrent.Future;

import client.ObsCaptor;
import observer.Subject;

/**
 * CaptorAsync is an interface used as proxy for Displays.
 * @author jgarnier
 */
public interface CaptorAsync extends Subject<ObsCaptor> {

	/**
	 * getValue will create the callable and returns its result as Future thank to scheduler.
	 * @return a Future which will contain the state of CaptorMonitor according to its diffusion
	 */
	public Future<Integer> getValue();

}
