package com.hybridtheory.mozzarella.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemsList;
import com.hybridtheory.mozzarella.persistence.StudentRepository;

@RestController
public class StudentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
    @Autowired
    private StudentRepository studentRepository;

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
    	LOGGER.info("query over"+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
    	return new ResponseEntity<Iterable<Student>>(studentsFound, HttpStatus.OK);   		
    }
    
    @RequestMapping(value="/students/{ids}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Student>> getStudents(@PathVariable("ids") String ids) {
    	return new ResponseEntity<List<Student>>(getStudentsByIds(IdSplitter.getIds(ids)),HttpStatus.OK);
    }
   
    @RequestMapping(value="/students/{ids}/learnitemslists/", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<LearnItemsList>> getLearnItemsLists(@PathVariable("ids") String ids) {
        return getLearnItemsLists(ids, "");
    }
    
    @RequestMapping(value="/students/{ids}/learnitemslists/{learnItemListIds}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<LearnItemsList>> getLearnItemsLists(@PathVariable("ids") String ids, @PathVariable("learnItemListIds") String learnItemsListIds) {
    	List<Integer> learnItemListIdInts = IdSplitter.getIds(learnItemsListIds);
    	List<Integer> idInts = IdSplitter.getIds(ids);
    	
    	List<LearnItemsList> learnItemsLists = getLearnItemsListsForUsers(getStudentsByIds(idInts),learnItemListIdInts);
    	
    	return new ResponseEntity<List<LearnItemsList>>(learnItemsLists,HttpStatus.OK);
    }
    
    
	@RequestMapping(value="/students/{ids}/learnitemslists/{learnItemListIds}/learnItems", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<LearnItem>> getLearnItems(@PathVariable("ids") String ids, 
    		@PathVariable("learnItemListIds") String learnItemsListIds, @RequestParam("numberOfLearnItems") Integer numberOfLearnItems) {
    	
    	Iterable<Student> students = getStudentsByIds(IdSplitter.getIds(ids));
    	List<Student> studentList = StreamSupport.stream(students.spliterator(), false).collect(Collectors.toList());
    	Student student;
    	
    	if(studentList.size()==1){
    		student = studentList.get(0);
    	} else {
    		return new ResponseEntity<List<LearnItem>>(HttpStatus.BAD_REQUEST);
    	}
    	
    	return new ResponseEntity<List<LearnItem>>(student.getLearnItemsToPractice(IdSplitter.getIds(learnItemsListIds), numberOfLearnItems),HttpStatus.OK);			
    						
    }
    
    private List<Student> getStudentsByIds(List<Integer> ids){
    	Iterable<Student> studentsWithIds = studentRepository.findAll(ids);
    	return StreamSupport.stream(studentsWithIds.spliterator(),false).collect(Collectors.toList());
    }
    
    private List<LearnItemsList> getLearnItemsListsForUsers(List<Student> students, List<Integer> learnItemsListIds){
    	//TODO WRONG! navie implementation, causes additional queries for each student, unacceptably slow
    	//create a method in learnitemlistrepository that queries by userid insted
    	return students.stream()
                .map(Student::getLearnItemLists)
                .flatMap(list -> list.stream())
                .filter(learnItemList->learnItemsListIds.size()==0 || learnItemsListIds.contains(learnItemList.getId()))
                .collect(Collectors.toList());
    }
}
