package diffusion;

import java.util.List;
import java.util.concurrent.Future;

import capteur.Capteur;
import client.ObsCapteur;

public class DiffusionAtomique implements Diffusion {

	private List<Integer> list;
	
	@Override
	public Future execute(Capteur c) {
		list.add(c.getValue());
		return null;
	}

	@Override
	public Integer getValue(ObsCapteur obs) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
