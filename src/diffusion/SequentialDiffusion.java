package diffusion;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import canal.ObsCaptorAsync;
import capteur.CaptorMonitor;
import client.ObsCaptor;

/**
 * SequentialDiffusion sends one state captor only when all displays have received the last one. 
 * The result is a sublist of captor states. It inherits from Diffusion for the Strategy pattern. 
 * CaptorMonitor calls whatever the diffusion execute method to update UI.
 * @author jgarnier
 */
public class SequentialDiffusion implements Diffusion {

	/**
	 * lock is a boolean value used to enable or disable the process to send update to canals.
	 */
	private boolean lock;
	
	/**
	 * currentState is the state that we are going to send or already sent to canals.
	 */
	private Integer currentState;
	
	/**
	 * callers is a set used to know how many ObsCaptor have requested the current state of the diffusion. It allows
	 * the diffusion to send a new value as soon as all ObsCaptor have received the last value.
	 */
	private Set<ObsCaptor> callers;
	
	/**
	 * canals is a list of ObsCaptorAsync through AtomicDiffusion send update callables.
	 */
	private List<ObsCaptorAsync> canals;
	
	/**
	 * SequentialDiffusion only needs to know all canals in order to send update callables.
	 * Alternatively, it initializes all values such as lock to false or currentState to 0.
	 * @param canals : list of canals connected to displays
	 */
	public SequentialDiffusion(List<ObsCaptorAsync> canaux) {
		this.lock = false;
		this.canals = canaux;
		this.callers = new HashSet<ObsCaptor>();
		this.currentState = 0;
	}
	
	/**
	 * execute is called each time CaptorMonitor updates its state. According to the diffusion,
	 * it saves or not the new state of captorMonitor and notify or not displays of the new value.
	 * @param c : c is the ref of captor monitor which has updated its state 
	 */
	@Override
	public void execute(CaptorMonitor c) {
		// One canal is associated to one ObsCaptor so if both are equal it means that each ObsCaptor has received
		// the current value so we can clear the Set
		if(callers.size() == canals.size()) {
			lock = false;
			callers.clear();
		}
		// In order to set a new value and call update to each Canal again
		if(callers.isEmpty() && !lock) {
			lock = true;
			currentState = c.getState();
			for(ObsCaptorAsync canal : canals)
				canal.update(c);
		}
	}

	/**
	 * getValue returns the available state according to the diffusion.
	 * It also keeps a track on obs in order to know if all ObsCapteurs have received the available state.
	 * @param obs : obs asking for the available state.
	 * @return currentState
	 */
	@Override
	public Integer getValue(ObsCaptor obs) {
		// We add obs in our set because our set will protect us to one ObsCaptor calling several times getValue 
		callers.add(obs);
		return currentState;
	}
	

}
