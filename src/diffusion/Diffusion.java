package diffusion;

import canal.ObsCaptorAsync;
import capteur.CaptorMonitor;
import client.ObsCaptor;
import observer.Subject;

/**
 * Diffusion is the strategy interface of diffusion algorithm.
 * @author jgarnier
 */
public interface Diffusion extends Subject<ObsCaptorAsync> {
	
	/**
	 * execute is called everytime the captor monitor is updated.
	 * @param c : c is the reference of the captor monitor used for retrieve its state
	 */
	public void execute(CaptorMonitor c);
	
	/**
	 * getValue is called by captor monitor each time a callable is asking its value. 
	 * The role of the diffusion is to manage which value the callable will get.
	 * @return the state according to the algorithm
	 */
	public Integer getValue();

}
