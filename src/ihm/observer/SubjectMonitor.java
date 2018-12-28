package ihm.observer;

import observer.Subject;

/**
 * SubjectMonitor is an interface used by CaptorMonitor and DisplayMonitor in order to be observed by ValueUI 
 */
public interface SubjectMonitor extends Subject<ObsMonitor> {
	public Integer getState();
}
