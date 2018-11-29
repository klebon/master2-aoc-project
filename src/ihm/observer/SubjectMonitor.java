package ihm.observer;

import observer.Subject;

/**
 * SubjectMonitor is an interface used by DisplayMonitor in order to be observed by DisplayMonitor 
 */
public interface SubjectMonitor extends Subject<ObsMonitor> {
	public Integer getState();
}
