package com.hybridtheory.mozarella.eventhandling;

import com.hybridtheory.mozarella.eventhandling.event.Event;

public interface Observable<T> {

	public void notifyObservers(Event<T> e);

	public void acceptObserver(Observer observer);
}