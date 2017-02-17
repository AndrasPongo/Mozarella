package com.hybridtheory.mozarella.eventhandling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hybridtheory.mozarella.eventhandling.event.Event;
import com.hybridtheory.mozarella.eventhandling.result.NewResultAvailableEvent;
import com.hybridtheory.mozarella.persistence.repository.LearnItemRepository;
import com.hybridtheory.mozarella.persistence.repository.ResultRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentItemRecordRepository;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.PriorityCalculator;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;

@Component
public class NewResultAvailableEventListener implements EventListener {
	
	EventEmitter emitter;
	
	@Autowired
	LearnItemRepository learnItemRepository;
	
	@Autowired
	ResultRepository resultRepository;
	
	@Autowired
	StudentItemRecordRepository studentItemRecordRepository;
	
	@Autowired
	PriorityCalculator priorityCalculator;
	
	@Autowired
	NewResultAvailableEventListener(EventEmitter emitter){
		this.emitter = emitter;
		emitter.subscribe(this, NewResultAvailableEvent.class);
	}
	
	@Override
	public void beNotifiedOfEvent(Event e) {
		NewResultAvailableEvent event = (NewResultAvailableEvent) e;
		
		Result result = event.getResult();
		Student student = result.getStudent();
		LearnItem learnItem = result.getLearnItem();
		
		//1. persist the result
		resultRepository.save(result);
		
		//2. assign a new priority to the learnItem, and save it
		Integer priority = priorityCalculator.calculatePriority(student, learnItem);
		
	}

}
