package com.hybridtheory.mozarella.eventhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hybridtheory.mozarella.api.StudentController;
import com.hybridtheory.mozarella.eventhandling.event.Event;
import com.hybridtheory.mozarella.eventhandling.result.NewResultAvailableEvent;
import com.hybridtheory.mozarella.persistence.repository.LearnItemRepository;
import com.hybridtheory.mozarella.persistence.repository.ResultRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentItemRecordRepository;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.StudentItemRecord;
import com.hybridtheory.mozarella.wordteacher.priority.PriorityCalculator;

@Component
public class NewResultAvailableEventListener implements EventListener {
	
	private EventEmitter emitter;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	private LearnItemRepository learnItemRepository;
	
	@Autowired
	private ResultRepository resultRepository;
	
	@Autowired
	private StudentItemRecordRepository studentItemRecordRepository;
	
	@Autowired
	private PriorityCalculator priorityCalculator;
	
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
		
		LOGGER.debug("processing new result for student "+student.getId()+" and learnItem "+learnItem.getId()+" STARTED");
		
		//1. persist the result
		resultRepository.save(result);
		
		//2. assign a new priority to the learnItem, and save it
		handleStudentItemRecord(student, learnItem);
		
		LOGGER.debug("processing new result for student "+student.getId()+" and learnItem "+learnItem.getId()+" ENDED");
	}
	
	public void handleStudentItemRecord(Student student, LearnItem learnItem){
		
		Double priority = priorityCalculator.calculatePriority(student, learnItem);
		StudentItemRecord studentItemRecord = studentItemRecordRepository.getStudentItemRecordForStudentAndLearnItemList(student.getId(), learnItem.getId());
		
		if(studentItemRecord==null){
			studentItemRecord = new StudentItemRecord(student,learnItem);
		}
		
		studentItemRecord.setPriority(priority);
		studentItemRecordRepository.save(studentItemRecord);
	}

}
