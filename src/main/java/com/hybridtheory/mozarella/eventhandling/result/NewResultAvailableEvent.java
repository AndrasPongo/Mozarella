package com.hybridtheory.mozarella.eventhandling.result;

import com.hybridtheory.mozarella.eventhandling.event.Event;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;

public class NewResultAvailableEvent implements Event{

	Result result;
	
	public NewResultAvailableEvent(Result result){
		this.result = result;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
}
