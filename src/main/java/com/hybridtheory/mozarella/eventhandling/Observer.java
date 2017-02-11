package com.hybridtheory.mozarella.eventhandling;

import com.hybridtheory.mozarella.eventhandling.event.Event;

public interface Observer {
	public void subscribeToObservable(Observable observable);
	public void beNotifiedOfEvent(Event e);
}
