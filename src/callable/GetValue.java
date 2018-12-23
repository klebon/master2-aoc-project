package callable;

import java.util.concurrent.Callable;

import captor.memento.CaptorState;

/**
 * GetValue is a callable used by Display in order to get value of CaptorState, a state of CaptorOriginator.
 * So it inherits from Callable and returns an Integer.
 */
public class GetValue implements Callable<Integer> {
	
	/**
	 * CaptorState which contains the state that we are looking for
	 */
	private CaptorState mCaptorState;
	
	/**
	 * A simple constructor mapping the references to attributes.
	 * @param captorState that will be mapped
	 */
	public GetValue(CaptorState captorState) {
		this.mCaptorState = captorState;
	}

	/**
	 * call is a method inherited which will be called by the scheduler. It will return the value of mCaptorState 
	 */
	@Override
	public Integer call() throws Exception {
		return mCaptorState.getState();
	}

}
