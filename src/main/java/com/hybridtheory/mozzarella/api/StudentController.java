package com.hybridtheory.mozzarella.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
    @RequestMapping("/students")
    public Iterable<Student> listStudents() {
        return studentRepository.findAll();   		
    }
    
    @RequestMapping("/students/{id}")
    //public Student getStudent(@RequestParam(value="id", defaultValue="1") int id) {
    public Iterable<Student> getStudent(@PathVariable("id") int id) {
    	//JUST FOR THE TEST *****************************************
        Student initStudent1 = new Student();
        initStudent1.setName("initStudent1");
        //initStudent.setOwnPet(new CubeFish());
        
        studentRepository.save(initStudent1);
        Student initStudent2 = new Student();
        initStudent2.setName("initStudent2");
        //initStudent.setOwnPet(new CubeFish());
        
        studentRepository.save(initStudent2);
    	//JUST FOR THE TEST *****************************************
        
    	return studentRepository.findAll();
    }
   
    
}
