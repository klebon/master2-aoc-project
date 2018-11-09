package canal;

import java.util.concurrent.Future;

import capteur.Capteur;

public interface CanalProxy {
	
	public Future update(Capteur c);

}
