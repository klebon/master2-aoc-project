package captor.memento;

import captor.Captor;

public interface CaptorOriginator extends Captor {

	public CaptorState createMemento();
	public void restoreMemento(CaptorState state);
	
}
