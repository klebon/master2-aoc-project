package callable;

import java.util.concurrent.Callable;

import client.ObsCapteur;

public class Update implements Callable {

	private ObsCapteur obs;
	
	public Update(ObsCapteur obs) {
		this.obs = obs;
	}
	
	@Override
	public Object call() throws Exception {
		obs.update(null);
		return null;
	}

}
