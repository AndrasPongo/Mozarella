package com.hybridtheory.mozarella.eventhandling;

import com.hybridtheory.mozarella.eventhandling.event.Event;

public interface EventListener {
	public void beNotifiedOfEvent(Event e);
}
