package com.hybridtheory.mozarella.eventhandling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolEventEmitter extends EventEmitter {
	
	public CachedThreadPoolEventEmitter(){
		super(Executors.newCachedThreadPool());
	}
	
	public ExecutorService getExecutorService(){
		return (ExecutorService) super.executor;
	}
	
}