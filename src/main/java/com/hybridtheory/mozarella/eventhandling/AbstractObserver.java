package com.hybridtheory.mozarella.eventhandling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hybridtheory.mozarella.eventhandling.event.Event;

public abstract class AbstractObserver implements Observer {
	ExecutorService executor = Executors.newSingleThreadExecutor();

	@Override
	public void subscribeToObservable(Observable observable) {
		observable.acceptObserver(this);
	}

	@Override
	public abstract void beNotifiedOfEvent(Event e);
	
}
