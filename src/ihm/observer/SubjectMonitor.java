package ihm.observer;

import observer.Subject;

/**
 * TODO: Commenter le code
 */
public interface SubjectMonitor extends Subject<ObsMonitor> {
	public Integer getState();
}
