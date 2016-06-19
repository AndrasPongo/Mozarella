package com.hybridtheory.mozarella.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import com.hybridtheory.mozarella.persistence.StudentItemRecordRepository;
import com.hybridtheory.mozarella.persistence.StudentRepository;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.utils.IdSplitter;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.StudentItemRecord;

import jersey.repackaged.com.google.common.collect.Lists;

@RestController
public class StudentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private LearnItemRepository learnItemRepository;
    
    @Autowired
    private StudentItemRecordRepository studentItemRecordRepository;

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
		
		List<LearnItem> learnItemsToReturn = learnItemRepository.getAllLearnItemsForStudent(studentId, learnItemListIds, new PageRequest(0,numberOfLearnItems));
    	
		return new ResponseEntity<List<LearnItem>>(learnItemsToReturn,HttpStatus.OK);						
    }
	
    @RequestMapping(value="/students/{id}/learnitems/{learnItemId}/results", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity postResult(@PathVariable("id") Integer studentId, @RequestParam("result") Boolean result, @PathVariable("learnItemId") Integer learnItemId) {
    	StudentItemRecord record = studentItemRecordRepository.getStudentItemRecordForStudentAndLearnItemList(studentId, learnItemId);
    	if(record == null){
    		Student student = studentRepository.findOne(studentId);
    		LearnItem learnItem = learnItemRepository.findOne(learnItemId); 
    		record = new StudentItemRecord(student,learnItem);
    		record.registerResult(result);
    		List<StudentItemRecord> recordList = new ArrayList<StudentItemRecord>();
    		recordList.add(record);
    		studentItemRecordRepository.save(recordList);
    	}
    	record.registerResult(result);
    	
    	return new ResponseEntity(HttpStatus.OK);	
    }
    
    private List<Student> getStudentsByIds(List<Integer> ids){
    	Iterable<Student> studentsWithIds = studentRepository.findAll(ids);
    	return Lists.newArrayList(studentsWithIds);
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
