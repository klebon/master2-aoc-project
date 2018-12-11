package captor.memento;

public class CaptorState {
	
	private final Integer mState;
	
	public CaptorState(Integer state) {
		this.mState = new Integer(state);
	}
	
	public Integer getState() {
		return this.mState;
	}
	
}
