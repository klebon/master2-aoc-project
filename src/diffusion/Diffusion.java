package diffusion;

import java.util.concurrent.Future;

import capteur.Capteur;

public interface Diffusion {
	
	public Future execute(Capteur c);


}
