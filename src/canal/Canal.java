package canal;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import callable.GetValue;
import callable.Update;
import capteur.Capteur;
import capteur.CapteurMoniteur;
import client.ObsCapteur;
import scheduler.SchedulerMoniteur;

public class Canal implements ObsCapteurAsync, CapteurAsync {
	
	private CapteurMoniteur capteur;
	private ObsCapteur obs;
	private SchedulerMoniteur scheduler;
	
	public Canal(CapteurMoniteur capteur, ObsCapteur obs, SchedulerMoniteur scheduler) {
		this.capteur = capteur;
		this.obs = obs;
		this.scheduler = scheduler;
	}

	@Override
	public Future<Object> update(Capteur c) {
		Callable<Object> update = new Update(obs);
		return scheduler.scheduleUpdate(update, 500L, TimeUnit.MILLISECONDS);
	}

	@Override
	public Future<Integer> getValue() {
		Callable<Integer> gv = new GetValue(capteur, obs);
		return scheduler.scheduleGetValue(gv, 500L, TimeUnit.MILLISECONDS);
	}

}
