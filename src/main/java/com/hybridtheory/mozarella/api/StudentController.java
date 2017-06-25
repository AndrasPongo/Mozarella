package com.hybridtheory.mozarella.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hybridtheory.mozarella.eventhandling.EventEmitter;
import com.hybridtheory.mozarella.eventhandling.result.NewResultAvailableEvent;
import com.hybridtheory.mozarella.eventhandling.result.StudentRegisteredEvent;
import com.hybridtheory.mozarella.persistence.repository.LearnItemListRepository;
import com.hybridtheory.mozarella.persistence.repository.LearnItemRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentItemRecordRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentRepository;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.users.StudentFactory;
import com.hybridtheory.mozarella.utils.IdSplitter;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;

import jersey.repackaged.com.google.common.collect.Lists;

@RestController
public class StudentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private LearnItemRepository learnItemRepository;
    
    @Autowired
    private LearnItemListRepository learnItemListRepository;
    
    @Autowired
    private StudentItemRecordRepository studentItemRecordRepository;
    
    @Autowired
    private StudentFactory studentFactory;
    
    @Autowired
    private EventEmitter eventEmitter;

    @RequestMapping(value="/api/students", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> create(@RequestBody Student student) {
        studentRepository.save(studentFactory.createStudent(student));
        
        StudentRegisteredEvent userRegisteredEvent = new StudentRegisteredEvent(student);
        eventEmitter.publish(userRegisteredEvent);
        
        return new ResponseEntity<Integer>(student.getId(),HttpStatus.CREATED);
    }
    
    @RequestMapping(value="/api/students", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Iterable<Student>> listStudents(@RequestParam("name") Optional<String> username) {
    	LOGGER.info("/students controller method call"+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
    	Iterable<Student> studentsFound;
    	
    	if(username.isPresent()){
    		studentsFound = studentRepository.findByName(username.get());
    	} else {
    		studentsFound = studentRepository.findAll();
    	}
    	
    	return new ResponseEntity<Iterable<Student>>(studentsFound, HttpStatus.OK);   		
    }
    
    @RequestMapping(value="/api/students/{ids}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Student>> getStudents(@PathVariable("ids") String ids) {
    	return new ResponseEntity<List<Student>>(getStudentsByIds(IdSplitter.getIds(ids)),HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/students/{studentid}/learnitemlists/{listid}/learnitems", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<LearnItem>> getLearnItemsToLearn(@PathVariable("studentid") Integer studentId, @PathVariable("listid") Integer listId, @RequestParam("count") Integer count) {
    	List<Integer> listIds = new ArrayList<>(); //repo is for multiple ids, but in practice we only send one
    	listIds.add(listId);
    	List<LearnItem> learnItems = learnItemRepository.getAllLearnItemsForStudent(studentId, listIds, new PageRequest(0, count));
    	
    	return new ResponseEntity<>(learnItems, HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/students/{studentid}/learnitemlists", method=RequestMethod.POST)
    public ResponseEntity<Object> associateStudentWithLearnItemList(@PathVariable("studentid") Integer studentId, @RequestBody LearnItemList list) {
    	Student student = studentRepository.findOne(studentId);

    	if(student==null || list==null){
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	
    	student.associateWithLearnItemsList(list);
    	studentRepository.save(student);
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/students/{studentid}/learnitemlists", method=RequestMethod.GET)
    public ResponseEntity<List<LearnItemList>> getLearnItemListsOfStudent(@PathVariable("studentid") Integer studentId, @RequestParam("pagenumber") Integer pageNumber, @RequestParam("pagesize") Integer pageSize) {
    	PageRequest pageRequest = new PageRequest(pageNumber,pageSize);
    	List<LearnItemList> result = learnItemListRepository.getLearnItemListsForStudent(studentId, pageRequest);
    	
    	return new ResponseEntity<List<LearnItemList>>(result,HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/students/{studentid}/learnitemlists/{listid}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> disssociateStudentWithLearnItemList(@PathVariable("studentid") Integer studentId, @PathVariable("listid") Integer listId) {
    	Student student = studentRepository.findOne(studentId);
    	LearnItemList list = learnItemListRepository.findOne(listId);
    	
    	if(student==null || list==null){
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
    	
    	student.disAssociateWithLearnItemsList(list);
    	studentRepository.save(student);
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/students/{studentid}/learnitemlists/{listid}/results", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postResult(@PathVariable("studentid") Integer studentId, @PathVariable("listid") Integer listId, @RequestBody Result result) {
    	LearnItem learnItem = learnItemRepository.findOne(result.getLearnItem().getId());
    	Student student = studentRepository.findOne(result.getStudent().getId());
    	
    	LOGGER.debug("learnItem.getLearnItemsList"+learnItem.getLearnItemsList());
    	//LOGGER.debug("learnItem.getLearnItemsList"+learnItem.getLearnItemsList());
    	
    	//TODO
    	if(learnItem.getLearnItemsList().getId().equals(listId) && student.getId().equals(studentId)){
    		result.setStudent(student);
    		result.setLearnItem(learnItem);
    		
    		eventEmitter.publish(new NewResultAvailableEvent(result));
    	} else {
    		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
    	}
    	
    	return new ResponseEntity<Object>(HttpStatus.OK);
    }
    
    private List<Student> getStudentsByIds(List<Integer> ids){
    	Iterable<Student> studentsWithIds = studentRepository.findAll(ids);
    	return Lists.newArrayList(studentsWithIds);
    }

}
