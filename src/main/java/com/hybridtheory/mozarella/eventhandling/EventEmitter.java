package com.hybridtheory.mozarella.eventhandling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hybridtheory.mozarella.api.StudentController;
import com.hybridtheory.mozarella.eventhandling.event.Event;

public class EventEmitter{

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
	private Map<String,List<EventListener>> listeners = new HashMap<String,List<EventListener>>();
	protected ExecutorService executor;
	
	public EventEmitter(){
	}
	
	public void publish(Event e) {
		if(executor==null || executor.isTerminated()){
			executor = Executors.newCachedThreadPool();
		}
		
		List<EventListener> listenersOfEvent = listeners.get(e.getClass().toString());
		if(listenersOfEvent!=null){
			listeners.get(e.getClass().toString()).stream().forEach(listener->{
				LOGGER.debug("emitting event of type e.getClass().toString()");
				
				executor.execute(()->listener.beNotifiedOfEvent(e));
			});
		}
		
	}

	public void subscribe(EventListener listener,Class<?> eventClass) {
		
		assert Arrays.asList(eventClass.getInterfaces()).contains(Event.class);
		
		List<EventListener> listenersThatListenToIt = listeners.get(eventClass.toString());
		
		if(listenersThatListenToIt!=null){
			listenersThatListenToIt.add(listener);
		} else {
			listenersThatListenToIt = new ArrayList<EventListener>();
			listenersThatListenToIt.add(listener);
			listeners.put(eventClass.toString(),listenersThatListenToIt);
		}
		
	}
	
	public ExecutorService getExecutorService(){
		return executor;
	}
}