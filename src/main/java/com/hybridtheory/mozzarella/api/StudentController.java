package com.hybridtheory.mozzarella.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozzarella.persistence.StudentRepository;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @RequestMapping(value = "/")
    public ResponseEntity<Student> get() {

        Student initStudent = new Student();
        initStudent.setName("initStudent");
        //initStudent.setOwnPet(new CubeFish());
        
        studentRepository.save(initStudent);

        return new ResponseEntity<Student>(initStudent, HttpStatus.OK);
    }
    
    @RequestMapping(value="/students", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Iterable<Student> listStudents() {
        return studentRepository.findAll();   		
    }
    
    @RequestMapping(value="/students/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Student getStudent(@PathVariable("id") int id) {
    	return studentRepository.findOne(id);
    }
   
    
}
