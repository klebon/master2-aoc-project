package observer;

/**
 * Subject template
 */
public interface Subject<T> {
	public void attach(T o);
	public void detach(T o);
	public void notifyObs();
}
