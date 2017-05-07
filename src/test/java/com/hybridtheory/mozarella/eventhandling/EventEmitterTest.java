package com.hybridtheory.mozarella.eventhandling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hybridtheory.mozarella.authorization.aspects.AspectTestConfig;
import com.hybridtheory.mozarella.configuration.authorization.AspectConfiguration;
import com.hybridtheory.mozarella.eventhandling.event.Event;
import com.hybridtheory.mozarella.eventhandling.result.StudentRegisteredEvent;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozzarella.api.ApplicationTests;

@RunWith(SpringJUnit4ClassRunner.class)
public class EventEmitterTest extends ApplicationTests {

	@Test
	public void test_emitter_notifies_listener() throws InterruptedException {
		//given
		EventEmitter emitter = new EventEmitter();	
		EventListener listener = new StudentRegisteredEventListener(emitter);
		
		((StudentRegisteredEventListener)listener).setApiEndpoint("");
		((StudentRegisteredEventListener)listener).setApiKey("");
		
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
		verify(listener, times(1)).beNotifiedOfEvent(event);
	}

}
