package captor.memento;

/**
 * CaptorState is the Memento class of Memento Pattern.
 * Its main goal is to encapsulate the Originator state.
 */
public class CaptorState {
	
	/**
	 * State of Originator
	 */
	private final Integer mState;
	
	/**
	 * A simple constructor making a copy of the Originator State
	 * @param state is the state of Originator that will be copied
	 */
	public CaptorState(Integer state) {
		this.mState = new Integer(state);
	}
	
	/**
	 * Getter of state
	 * @return state of Originator
	 */
	public synchronized Integer getState() {
		return this.mState;
	}
	
}
