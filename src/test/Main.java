package test;

import java.util.ArrayList;
import java.util.List;

import canal.Canal;
import canal.ObsCaptorAsync;
import capteur.CaptorMonitor;
import capteur.CaptorThread;
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
		Canal c1 = new Canal(c, a1, scheduler);
		Canal c2 = new Canal(c, a2, scheduler);
		Canal c3 = new Canal(c, a3, scheduler);
		Canal c4 = new Canal(c, a4, scheduler);
		
		// Set the canal to afficheur
		a1.setCanal(c1);
		a2.setCanal(c2);
		a3.setCanal(c3);
		a4.setCanal(c4);
		// Then add it to our list
		List<ObsCaptorAsync> myObs = new ArrayList<ObsCaptorAsync>();
		myObs.add(c1);
		myObs.add(c2);
		myObs.add(c3);
		myObs.add(c4);
		
		// We can instantiate the diffusion now
		Diffusion sequentialD = new SequentialDiffusion(myObs);
		Diffusion atomicD = new AtomicDiffusion(myObs);
		// Set it to our capteur
		c.setDiffusion(sequentialD);
		
		
		// UI
		ValueUI captorUI = new ValueUI("CaptorMonitor");
		ValueUI display1 = new ValueUI("DisplayMonitor");
		ValueUI display2 = new ValueUI("DisplayMonitor");
		ValueUI display3 = new ValueUI("DisplayMonitor");
		ValueUI display4 = new ValueUI("DisplayMonitor");
		
		captorUI.setSujet(c); captorUI.showView();
		display1.setSujet(a1); display1.showView();
		display2.setSujet(a2); display2.showView();
		display3.setSujet(a3); display3.showView();
		display4.setSujet(a4); display4.showView();
		
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
	
		while(true);
	}

}
