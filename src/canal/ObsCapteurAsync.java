package canal;

import java.util.concurrent.Future;

import capteur.Capteur;

public interface ObsCapteurAsync {
	
	public Future update(Capteur c);

}
