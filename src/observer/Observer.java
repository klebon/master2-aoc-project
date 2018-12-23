package observer;

/**
 * Observer template
 */
public interface Observer<T> {
	public void update(T subject);
}
