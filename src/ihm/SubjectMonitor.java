package ihm;

import observer.Subject;

public interface SubjectMonitor extends Subject<ObsMonitor> {
	public Integer getState();
}
