package diffusion;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import canal.ObsCaptorAsync;
import capteur.CaptorMonitor;
import client.ObsCaptor;

/**
 * AtomicDiffusion sends all of captor state stored in a queue to each canal. It never sends a new captor state until all of 
 * canals would receive the last state. It inherits from Diffusion for the Strategy pattern. CaptorMonitor calls whatever
 * the diffusion execute method to update UI.
 * @author jgarnier
 */
public class AtomicDiffusion implements Diffusion {

	/**
	 * lock is a boolean value used to enable or disable the process to send update to canals.
	 */
	private boolean lock;
	
	/**
	 * currentState is the state that we are going to send or already sent to canals.
	 */
	private Integer currentState;
	
	/**
	 * queue is used to store all states of CaptorMonitor to keep a track of them because AtomicDiffusion has to
	 * send all the states of CaptorMonitor to canals.
	 */
	private Queue<Integer> queue;
	
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
	 * AtomicDiffusion only needs to know all canals in order to send update callables.
	 * Alternatively, it initializes all values such as lock to false or currentState to 0.
	 * @param canals : list of canals connected to displays
	 */
	public AtomicDiffusion(List<ObsCaptorAsync> canals) {
		this.canals = canals;
		this.lock = false;
		this.queue = new ArrayDeque<Integer>();
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
		// We store the value neither all displays received the update callable nor not
		queue.add(c.getState());
		// If all of canals received the new value, we can now set lock to false
		if(callers.size() == canals.size())
			lock = false;
		// We are going to check if we can send the new value 
		if(callers.isEmpty() && !lock) {
			// We lock the process to send a new value
			lock = true;
			// And now we get the next value which is the head of our queue
			currentState = queue.poll();
			// And we notice canals a new value is available
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
