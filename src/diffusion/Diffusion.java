package diffusion;

import canal.observer.ObsCaptorAsync;
import captor.memento.CaptorOriginator;
import observer.Subject;

/**
 * Diffusion is the strategy interface of diffusion algorithm.
 */
public interface Diffusion extends Subject<ObsCaptorAsync> {
	
	/**
	 * execute is called everytime the captor monitor is updated.
	 */
	public void execute(CaptorOriginator originator);
	
}
