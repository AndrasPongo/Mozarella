package com.hybridtheory.mozarella.eventhandling.result;

import org.springframework.stereotype.Component;

import com.hybridtheory.mozarella.eventhandling.AbstractObservable;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;

@Component
public class ResultEventEmitter extends AbstractObservable<Result> {
	
}
