package diffusion;

import java.util.ArrayList;
import java.util.List;

import canal.observer.ObsCaptorAsync;
import captor.CaptorMonitor;

/**
 * SequentialDiffusion sends one state captor only when all displays have received the last one. 
 * The result is a sublist of captor states. It inherits from Diffusion for the Strategy pattern. 
 * CaptorMonitor calls whatever the diffusion execute method to update UI.
 */
public class SequentialDiffusion implements Diffusion {

	/**
	 * lock is a boolean value used to enable or disable the process to send update to observers.
	 */
	private boolean lock;
	
	/**
	 * currentState is the state that we are going to send or already sent to observers.
	 */
	private Integer currentState;
	
	/**
	 * observers is a list of ObsCaptorAsync through AtomicDiffusion send update callables.
	 */
	private List<ObsCaptorAsync> observers;
	
	private int count;
	
	/**
	 * SequentialDiffusion only needs to know all observers in order to send update callables.
	 * Alternatively, it initializes all values such as lock to false or currentState to 0.
	 * @param observers : list of observers connected to displays
	 */
	public SequentialDiffusion() {
		this.lock = false;
		this.observers = new ArrayList<>();
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
		if(count == observers.size()) {
			lock = false;
		}
		// In order to set a new value and call update to each Canal again
		if(!lock) {
			lock = true;
			count = 0;
			currentState = c.getState();
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
		this.observers.remove(o);
	}

	@Override
	public void notifyObs() {}
	

}
