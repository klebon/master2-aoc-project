package main;

import canal.Canal;
import captor.CaptorMonitor;
import captor.CaptorThread;
import client.DisplayMonitor;
import client.DisplayThread;
import diffusion.AtomicDiffusion;
import diffusion.Diffusion;
import diffusion.SequentialDiffusion;
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
		Canal c1 = new Canal(c, scheduler); c1.attach(a1);
		Canal c2 = new Canal(c, scheduler); c2.attach(a2);
		Canal c3 = new Canal(c, scheduler); c3.attach(a3);
		Canal c4 = new Canal(c, scheduler); c4.attach(a4);
		
		// Then add it to our list
		
		// We can instantiate the diffusion now
		Diffusion sequentialD = new SequentialDiffusion();
		Diffusion atomicD = new AtomicDiffusion();
		
		sequentialD.attach(c1); atomicD.attach(c1);
		sequentialD.attach(c2); atomicD.attach(c2);
		sequentialD.attach(c3); atomicD.attach(c3);
		sequentialD.attach(c4); atomicD.attach(c4);
		if(args[0].equals("Atomique")) {
			c.setDiffusion(atomicD);
		} else {
			c.setDiffusion(sequentialD);
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
		
		// Manage threads
		CaptorThread captorthread = new CaptorThread(c);
		DisplayThread displayThread1 = new DisplayThread(a1);
		DisplayThread displayThread2 = new DisplayThread(a2);
		DisplayThread displayThread3 = new DisplayThread(a3);
		DisplayThread displayThread4 = new DisplayThread(a4);
		
		captorthread.setDaemon(true);
		captorthread.start();
	
		displayThread1.setDaemon(true);
		displayThread1.start();
		
		displayThread2.setDaemon(true);
		displayThread2.start();
		
		displayThread3.setDaemon(true);
		displayThread3.start();
		
		displayThread4.setDaemon(true);
		displayThread4.start();
	
	}

}
