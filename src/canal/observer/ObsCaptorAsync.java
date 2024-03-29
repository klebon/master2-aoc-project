package canal.observer;

import java.util.concurrent.Future;

import captor.memento.CaptorState;
import diffusion.Diffusion;
import observer.Observer;

/**
 * ObsCaptorAsync is an interface used as proxy for CaptorMonitor and Diffusion.
 */
public interface ObsCaptorAsync extends Observer<Diffusion> {
	
	/**
	 * update will create the callable and returns its result as Future thank to scheduler.
	 * @param c is the current state of CaptorOriginator when the diffusion execution method is called
	 * @return a Future without result
	 */
	public Future<Void> update(CaptorState c);

}
