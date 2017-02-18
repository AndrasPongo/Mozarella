package com.hybridtheory.mozarella.eventhandling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import com.hybridtheory.mozarella.eventhandling.event.Event;

public class EventEmitter{

	private Map<String,List<EventListener>> listeners = new HashMap<String,List<EventListener>>();
	protected ExecutorService executor = Executors.newCachedThreadPool();
	
	protected EventEmitter(ExecutorService executor){
		this.executor = executor;
	}
	
	public void publish(Event e) {
		listeners.get(e.getClass().toString()).stream().forEach(listener->{
			executor.execute(()->{listener.beNotifiedOfEvent(e);});
		});
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