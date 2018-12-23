package captor.impl;

/**
 * CaptorThread is the captor's behavior. It inherits from Thread because it will update the captor state 
 * independently the main thread such as a real captor measuring new value each second's half.
 */
public class CaptorThread extends Thread {
	
	/**
	 * CaptorMonitor is the object encapsulating the state of the captor 
	 */
	private CaptorMonitor mCaptorMonitor;
	
	/**
	 * CaptorThread needs a CaptorMonitor in order to update its state.
	 * @param c that will be mapped
	 */
	public CaptorThread(CaptorMonitor c) {
		this.mCaptorMonitor = c;
	}

	/**
	 * run is inherited from Thread and represents the captor behavior. 
	 * While the application is running, CaptorThread will update CaptorMonitor state and sleeps a lapse.
	 */
	@Override
	public void run() {
		while(true) {
			mCaptorMonitor.setValue();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
