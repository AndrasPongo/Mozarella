package com.hybridtheory.mozarella.eventhandling.event;

public abstract class Event<T> {
	
	T payload;
	
	public Event(T payload){
		this.payload = payload;
	}
	
	public T getPayload(){
		return payload;
	}
}
