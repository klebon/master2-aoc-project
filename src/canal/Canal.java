package canal;

import java.util.concurrent.Future;

import capteur.Capteur;
import client.Afficheur;

public class Canal implements ObsCapteurAsync, CapteurAsync {
	
	private Afficheur a;
	
	public Canal(Afficheur a) {
		this.a = a;
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
