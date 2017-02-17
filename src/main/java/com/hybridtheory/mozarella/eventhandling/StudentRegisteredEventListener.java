package com.hybridtheory.mozarella.eventhandling;

import org.springframework.stereotype.Component;

import com.hybridtheory.mozarella.eventhandling.event.Event;
import com.hybridtheory.mozarella.eventhandling.result.StudentRegisteredEvent;

@Component
public class StudentRegisteredEventListener implements EventListener {
	
	@Override
	public void beNotifiedOfEvent(Event e) {
		StudentRegisteredEvent studentRegisteredEvent = (StudentRegisteredEvent) e;
		System.out.println("Received student registered event! Student's name is: "+studentRegisteredEvent.getRegisteredStudent().getRole());
	}

}