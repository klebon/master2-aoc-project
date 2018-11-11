package diffusion;

import capteur.CaptorMonitor;
import client.ObsCaptor;

/**
 * Diffusion is the strategy interface of diffusion algorithm.
 * @author jgarnier
 */
public interface Diffusion {
	
	/**
	 * execute is called everytime the captor monitor is updated.
	 * @param c : c is the reference of the captor monitor used for retrieve its state
	 */
	public void execute(CaptorMonitor c);
	
	/**
	 * getValue is called by captor monitor each time a callable is asking its value. 
	 * The role of the diffusion is to manage which value the callable will get.
	 * @param obs : obs is the reference of the object asking for captor monitor state.
	 * @return the state according to the algorithm
	 */
	public Integer getValue(ObsCaptor obs);

}
