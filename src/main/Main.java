package main;

import canal.Canal;
import captor.impl.CaptorMonitor;
import captor.impl.CaptorThread;
import client.DisplayMonitor;
import diffusion.AtomicDiffusion;
import diffusion.Diffusion;
import diffusion.SequentialDiffusion;
import diffusion.VersionDiffusion;
import ihm.ValueUI;
import scheduler.SchedulerMonitor;

public class Main {

	public static void main(String[] args) {
		// First create SchedulerMonitor
		SchedulerMonitor scheduler = new SchedulerMonitor();
		// secondly create the captor
		CaptorMonitor c = new CaptorMonitor();
		// thirdly the display
		DisplayMonitor a1 = new DisplayMonitor();
		DisplayMonitor a2 = new DisplayMonitor();
		DisplayMonitor a3 = new DisplayMonitor();
		DisplayMonitor a4 = new DisplayMonitor();

		// Now the canal
		Canal c1 = new Canal(scheduler); c1.attach(a1);
		Canal c2 = new Canal(scheduler); c2.attach(a2);
		Canal c3 = new Canal(scheduler); c3.attach(a3);
		Canal c4 = new Canal(scheduler); c4.attach(a4);
				
		// We can instantiate diffusions now
		Diffusion sequentialD = new SequentialDiffusion();
		Diffusion atomicD = new AtomicDiffusion();
		Diffusion versionD = new VersionDiffusion();
		
		sequentialD.attach(c1); atomicD.attach(c1); versionD.attach(c1);
		sequentialD.attach(c2); atomicD.attach(c2); versionD.attach(c2);
		sequentialD.attach(c3); atomicD.attach(c3); versionD.attach(c3);
		sequentialD.attach(c4); atomicD.attach(c4); versionD.attach(c4);
		
		if(args[0].equals("Atomique")) {
			c.setDiffusion(atomicD);
		} else if(args[0].equals("Séquentielle")) {
			c.setDiffusion(sequentialD);
		} else if(args[0].equals("Version")){
			c.setDiffusion(versionD);
		}
		
		// UI
		ValueUI captorUI = new ValueUI("CaptorMonitor");
		ValueUI display1 = new ValueUI("DisplayMonitor");
		ValueUI display2 = new ValueUI("DisplayMonitor");
		ValueUI display3 = new ValueUI("DisplayMonitor");
		ValueUI display4 = new ValueUI("DisplayMonitor");
		
		c.attach(captorUI); captorUI.showView();
		a1.attach(display1); display1.showView();
		a2.attach(display2); display2.showView();
		a3.attach(display3); display3.showView();
		a4.attach(display4); display4.showView();
		
		// Manage thread
		CaptorThread captorthread = new CaptorThread(c);
		
		// All behavior threads are daemon to close them when the main thread is closed
		captorthread.setDaemon(true);
		captorthread.start();
	}
	

}
