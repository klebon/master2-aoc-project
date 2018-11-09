package callable;

import java.util.concurrent.Callable;
import capteur.CapteurMoniteur;
import client.ObsCapteur;

public class GetValue implements Callable<Integer> {
	
	private CapteurMoniteur c;
	private ObsCapteur obs;
	
	public GetValue(CapteurMoniteur c, ObsCapteur obs) {
		this.c = c;
		this.obs = obs;
	}

	@Override
	public Integer call() throws Exception {
		return c.getValue(obs);
	}

}
