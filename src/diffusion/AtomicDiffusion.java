package diffusion;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import canal.observer.ObsCaptorAsync;
import captor.CaptorMonitor;

/**
 * AtomicDiffusion sends all of captor state stored in a queue to each canal. It never sends a new captor state until all of 
 * observers would receive the last state. It inherits from Diffusion for the Strategy pattern. CaptorMonitor calls whatever
 * the diffusion execute method to update UI.
 */
public class AtomicDiffusion implements Diffusion {

	/**
	 * lock is a boolean value used to enable or disable the process to send update to observers.
	 */
	private boolean lock;
	
	/**
	 * currentState is the state that we are going to send or already sent to observers.
	 */
	private Integer currentState;
	
	/**
	 * queue is used to store all states of CaptorMonitor to keep a track of them because AtomicDiffusion has to
	 * send all the states of CaptorMonitor to observers.
	 */
	private Queue<Integer> queue;
	
	/**
	 * observers is a list of ObsCaptorAsync through AtomicDiffusion send update callables.
	 */
	private List<ObsCaptorAsync> observers;

	
	private int count;
	
	/**
	 * AtomicDiffusion only needs to know all observers in order to send update callables.
	 * Alternatively, it initializes all values such as lock to false or currentState to 0.
	 * @param observers : list of observers connected to displays
	 */
	public AtomicDiffusion() {
		this.lock = false;
		this.observers = new ArrayList<>();
		this.queue = new ArrayDeque<Integer>();
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
		// If all of observers received the new value, we can now set lock to false
		if(count == observers.size())
			lock = false;
		// We are going to check if we can send the new value 
		if(!lock) {
			// We lock the process to send a new value
			lock = true;
			// Reset the count
			count = 0;
			// And now we get the next value which is the head of our queue
			currentState = queue.poll();
			// And we notice observers a new value is available
			for(ObsCaptorAsync o : observers)
				o.update(c);
		}		
	}

	/**
	 * getValue returns the available state according to the diffusion.
	 * It also keeps a track on obs in order to know if all ObsCapteurs have received the available state.
	 * @param obs : obs asking for the available state.
	 * @return currentState
	 */
	@Override
	public Integer getValue() {
		// We add obs in our set because our set will protect us to one ObsCaptor calling several times getValue 
		count++;
		return currentState;
	}

	@Override
	public void attach(ObsCaptorAsync o) {
		this.observers.add(o);
	}

	@Override
	public void detach(ObsCaptorAsync o) {
		this.observers.add(o);
	}

	@Override
	public void notifyObs() {}
	
	

}
