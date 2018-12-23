package client.observer;

import canal.observer.CaptorAsync;
import observer.Observer;

/**
 * ObsCaptor is used by DisplayMonitor and is monitoring CaptorAsync (proxy)  
 */
public interface ObsCaptor extends Observer<CaptorAsync> {}
