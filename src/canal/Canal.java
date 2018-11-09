package canal;

import java.util.concurrent.Future;

import capteur.Capteur;
import client.Afficheur;
import client.ObsCapteur;

public class Canal implements ObsCapteurAsync, CapteurAsync {
	
	private ObsCapteur obs;
	
	public Canal(ObsCapteur obs) {
		this.obs = obs;
	}

	@Override
	public Future update(Capteur c) {

		// CREATE CALLABLE
		return null;
	}

	@Override
	public Future getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
