package client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import canal.CapteurAsync;
import capteur.Capteur;

public class Afficheur extends Thread implements ObsCapteur {
	
	private CapteurAsync canal;
	
	@Override
	public void update(Capteur c) {
		Future<Integer> f = canal.getValue();
		try {
			Integer i = (Integer) f.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	
	
}
