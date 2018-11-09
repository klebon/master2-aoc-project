package diffusion;

import java.util.List;
import java.util.concurrent.Future;

import capteur.Capteur;

public class DiffusionAtomique implements Diffusion {

	private List<Integer> list;
	
	@Override
	public Future execute(Capteur c) {
		list.add(c.getCompteur());
		return null;
	}
	

}
