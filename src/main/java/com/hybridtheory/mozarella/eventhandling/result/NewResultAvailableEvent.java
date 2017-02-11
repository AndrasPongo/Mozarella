package com.hybridtheory.mozarella.eventhandling.result;

import com.hybridtheory.mozarella.eventhandling.event.Event;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;

public class NewResultAvailableEvent extends Event<Result> {

	public NewResultAvailableEvent(Result r){
		super(r);
	}

}
