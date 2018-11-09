package callable;

import java.util.concurrent.Callable;
import capteur.CapteurMoniteur;

public class GetValue implements Callable {
	
	private CapteurMoniteur c;
	
	public GetValue(CapteurMoniteur c) {
		this.c = c;
	}

	@Override
	public Integer call() throws Exception {
		return c.getValue();
	}

}
