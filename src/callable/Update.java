package callable;

import java.util.concurrent.Callable;

import client.Afficheur;

public class Update implements Callable {

	private Afficheur a;
	
	public Update(Afficheur a) {
		this.a = a;
	}
	
	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
