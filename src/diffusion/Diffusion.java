package diffusion;

import java.util.concurrent.Future;

import capteur.Capteur;
import client.ObsCapteur;

public interface Diffusion {
	
	public Future execute(Capteur c);
	public Integer getValue(ObsCapteur obs);

}
