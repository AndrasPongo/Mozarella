package com.hybridtheory.mozarella.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hybridtheory.mozarella.persistence.LearnItemRepository;
import com.hybridtheory.mozarella.persistence.ResultContainerRepository;
import com.hybridtheory.mozarella.persistence.StudentRepository;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemsManager;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.ResultContainer;

@RestController
public class StudentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private LearnItemRepository learnItemRepository;
    
    @Autowired
    private ResultContainerRepository resultContainerRepository;

    @RequestMapping(value="/students", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Student> create(@RequestParam("credentialType") String credentialType, String value) {

        Student newStudent = new Student();
        newStudent.setName(value);
        
        studentRepository.save(newStudent);

        return new ResponseEntity<Student>(newStudent, HttpStatus.OK);
    }
    
    @RequestMapping(value="/students", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Iterable<Student>> listStudents() {
    	LOGGER.info("/students controller method call"+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
    	Iterable<Student> studentsFound = studentRepository.findAll();
    	return new ResponseEntity<Iterable<Student>>(studentsFound, HttpStatus.OK);   		
    }
    
    @RequestMapping(value="/students/{ids}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Student>> getStudents(@PathVariable("ids") String ids) {
    	return new ResponseEntity<List<Student>>(getStudentsByIds(IdSplitter.getIds(ids)),HttpStatus.OK);
    }
   
    @RequestMapping(value="/students/{ids}/learnitemslists/", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<LearnItemList>> getLearnItemsLists(@PathVariable("ids") String ids) {
        return getLearnItemsLists(ids, "");
    }
    
    @RequestMapping(value="/students/{ids}/learnitemslists/{learnItemListIds}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<LearnItemList>> getLearnItemsLists(@PathVariable("ids") String ids, @PathVariable("learnItemListIds") String learnItemsListIds) {
    	List<Integer> learnItemListIdInts = IdSplitter.getIds(learnItemsListIds);
    	List<Integer> idInts = IdSplitter.getIds(ids);
    	
    	List<LearnItemList> learnItemsLists = getLearnItemsListsForUsers(getStudentsByIds(idInts),learnItemListIdInts);
    	
    	return new ResponseEntity<List<LearnItemList>>(learnItemsLists,HttpStatus.OK);
    }
    
    
	@RequestMapping(value="/students/{ids}/learnitemslists/{learnItemListIds}/learnitems", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<LearnItem>> getLearnItems(@PathVariable("ids") String ids, 
    		@PathVariable("learnItemListIds") String learnItemsListIds, @RequestParam("number") Integer numberOfLearnItems) {
    	
		List<Integer> learnItemListIds = IdSplitter.getIds(learnItemsListIds);
		List<Integer> studentIds = IdSplitter.getIds(ids);
    	
		Integer studentId;
		
    	if(studentIds.size()==1){
    		studentId = studentIds.get(0);
    	} else {
    		return new ResponseEntity<List<LearnItem>>(HttpStatus.BAD_REQUEST);
    	}
		
		List<LearnItem> learnItemsToReturn = learnItemRepository.getLearnItemsForStudent(studentId, learnItemListIds, new PageRequest(0,numberOfLearnItems));
		LOGGER.info("number of learn items already associated with a result container: "+learnItemsToReturn.size());
		
		if(learnItemsToReturn.size()<numberOfLearnItems){
			LOGGER.info("fetching additional learn items");
    		List<LearnItem> newLearnItems = learnItemRepository.getNewLearnItemsForStudent(studentId, learnItemListIds, new PageRequest(0,numberOfLearnItems-learnItemsToReturn.size()));
    		learnItemsToReturn.addAll(newLearnItems);
    	}
		
		return new ResponseEntity<List<LearnItem>>(learnItemsToReturn,HttpStatus.OK);						
    }
	
    @RequestMapping(value="/students/{id}/learnitems/{learnItemId}/results", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity postResult(@PathVariable("id") Integer studentId, @RequestParam("result") Boolean result, @PathVariable("learnItemId") Integer learnItemId) {
    	List<ResultContainer> resultContainers = resultContainerRepository.getResultContainerForStudentAndLearnItem(learnItemId, studentId);
    	ResultContainer resultContainer;
    	if(resultContainers.size()==0){
    		resultContainer = new ResultContainer(learnItemRepository.findOne(learnItemId));
    		Student student = studentRepository.findOne(studentId);
    		LearnItemsManager manager = student.getLearnItemManager();
    		manager.acceptResult(resultContainer, result);
    		resultContainerRepository.save(resultContainer);
    	} else {
    		resultContainer = resultContainers.get(0);
    	}
    	
    	resultContainer.registerResult(result);
    	resultContainerRepository.flush();
    	
    	return new ResponseEntity(HttpStatus.OK);
    }
    
    private List<Student> getStudentsByIds(List<Integer> ids){
    	Iterable<Student> studentsWithIds = studentRepository.findAll(ids);
    	return StreamSupport.stream(studentsWithIds.spliterator(),false).collect(Collectors.toList());
    }
    
    private List<LearnItemList> getLearnItemsListsForUsers(List<Student> students, List<Integer> learnItemsListIds){
    	//TODO WRONG! navie implementation, causes additional queries for each student, unacceptably slow
    	//create a method in learnitemlistrepository that queries by userid insted
    	return students.stream()
                .map(Student::getLearnItemLists)
                .flatMap(list -> list.stream())
                .filter(learnItemList->learnItemsListIds.size()==0 || learnItemsListIds.contains(learnItemList.getId()))
                .collect(Collectors.toList());
    }
}
