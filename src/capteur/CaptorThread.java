package capteur;

/**
 * CaptorThread is the captor's behavior. It inherits from Thread because it will update the captor state 
 * independently the main thread such as a real captor measuring new value each second.
 * @author jgarnier
 */
public class CaptorThread extends Thread {
	
	/**
	 * CaptorMonitor is the current state of the captor 
	 */
	private CaptorMonitor c;
	
	/**
	 * CaptorThread needs a CaptorMonitor in order to update its state.
	 * @param c
	 */
	public CaptorThread(CaptorMonitor c) {
		this.c = c;
	}

	/**
	 * run is inherited from Thread and represents the captor behavior. 
	 * While the application is running, CaptorThread will update CaptorMonitor state and sleeps a lapse.
	 */
	@Override
	public void run() {
		while(true) {
			c.setValue();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
