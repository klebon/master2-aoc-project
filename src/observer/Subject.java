package observer;

/**
 * TODO: Commenter le code
 */
public interface Subject<T> {
	public void attach(T o);
	public void detach(T o);
	public void notifyObs();
}
