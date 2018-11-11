package client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DisplayThread extends Thread {
	
	private DisplayMonitor display;
	
	public DisplayThread(DisplayMonitor display) {
		this.display = display;
	}

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
