package com.hybridtheory.mozarella.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hybridtheory.mozarella.persistence.repository.LearnItemRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentItemRecordRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentRepository;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.users.StudentFactory;
import com.hybridtheory.mozarella.utils.IdSplitter;

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
    
    @Autowired
    private StudentFactory studentFactory;

    @RequestMapping(value="/api/students", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody Student student) {
        studentRepository.save(studentFactory.createStudent(student));
        return new ResponseEntity(HttpStatus.CREATED);
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
    
    private List<Student> getStudentsByIds(List<Integer> ids){
    	Iterable<Student> studentsWithIds = studentRepository.findAll(ids);
    	return Lists.newArrayList(studentsWithIds);
    }

}
