package canal;

import java.util.concurrent.Future;

import capteur.CaptorMonitor;

/**
 * ObsCaptorAsync is an interface used as proxy for CaptorMonitor and Diffusion.
 * @author jgarnier
 */
public interface ObsCaptorAsync {
	
	/**
	 * update will create the callable and returns its result as Future thank to scheduler.
	 * @return a Future without result
	 */
	public Future<Object> update(CaptorMonitor c);

}
