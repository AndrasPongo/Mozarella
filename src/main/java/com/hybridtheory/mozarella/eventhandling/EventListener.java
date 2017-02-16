package com.hybridtheory.mozarella.eventhandling;

import com.hybridtheory.mozarella.eventhandling.event.Event;
import com.hybridtheory.mozarella.eventhandling.event.EventType;

public interface EventListener {
	public void beNotifiedOfEvent(Event e);
}
