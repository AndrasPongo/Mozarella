package com.hybridtheory.mozarella.eventhandling;

import java.util.List;
import java.util.ArrayList;

import com.hybridtheory.mozarella.eventhandling.event.Event;

public class AbstractObservable<T> implements Observable<T> {

	List<Observer> observers = new ArrayList<Observer>();
	
	public AbstractObservable(){
		
	}
	
	@Override
	public void notifyObservers(Event<T> e) {
		observers.stream().forEach(o->o.beNotifiedOfEvent(e));
	}

	@Override
	public void acceptObserver(Observer observer) {
		observers.add(observer);
	}
}