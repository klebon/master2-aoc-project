package client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * DisplayThread is the display behavior. It inherits from Thread because it will ask to display state its future 
 * and get the value of the future (because get is a blocking method). 
 */
public class DisplayThread extends Thread {
	
	/**
	 * DisplayMonitor is the current state of the display 
	 */
	private DisplayMonitor display;
	
	/**
	 * DisplayThread needs a DisplayMonitor in order to update its state.
	 * @param display
	 */
	public DisplayThread(DisplayMonitor display) {
		this.display = display;
	}

	/**
	 * run is inherited from Thread and represents the display behavior. 
	 * While the application is running, DisplayThread will ask to DisplayMonitor future and if the future is different of
	 * null, call the method get and update DisplayMonitor according to the received value.
	 */
	@Override
	public void run() {
		while(true) {
			Future<Integer> f = display.getF();
			if(f != null) {
				try {
					Integer value = f.get();
					display.setF(null);
					display.setValue(value);
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
