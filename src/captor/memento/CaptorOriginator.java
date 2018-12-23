package captor.memento;

/**
 * CaptorOriginator is the Originator interface of the Memento Pattern.
 */
public interface CaptorOriginator {

	public CaptorState createMemento();
	public void restoreMemento(CaptorState state);
	
}
