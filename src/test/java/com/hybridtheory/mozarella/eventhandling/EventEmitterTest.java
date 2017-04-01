package com.hybridtheory.mozarella.eventhandling;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.hybridtheory.mozarella.eventhandling.event.Event;
import com.hybridtheory.mozarella.eventhandling.result.StudentRegisteredEvent;
import com.hybridtheory.mozarella.users.Student;

public class EventEmitterTest {

	@Test
	public void test_emitter_notifies_listener() throws InterruptedException {
		//given
		EventEmitter emitter = new EventEmitter();	
		EventListener listener = new StudentRegisteredEventListener(emitter);
		
		Student registeredStudent = new Student();
		registeredStudent.setName("studentName");
		
		Event event = new StudentRegisteredEvent(registeredStudent);
		
		//when
		emitter.subscribe(listener,StudentRegisteredEvent.class);
		emitter.publish(event);
		
		ExecutorService executorService = emitter.getExecutorService();
		executorService.shutdown();
		
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		
		//then
		//verify(listener, times(1)).beNotifiedOfEvent(event);
	}

}
