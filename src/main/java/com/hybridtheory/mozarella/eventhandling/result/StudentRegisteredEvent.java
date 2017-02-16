package com.hybridtheory.mozarella.eventhandling.result;

import com.hybridtheory.mozarella.eventhandling.event.Event;
import com.hybridtheory.mozarella.users.Student;

public class StudentRegisteredEvent implements Event {
	
	private Student registeredStudent;
	
	public StudentRegisteredEvent(Student registeredStudent){
		this.registeredStudent = registeredStudent;
	}

	public Student getRegisteredStudent() {
		return registeredStudent;
	}
	
}
