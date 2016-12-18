package com.hybridtheory.mozarella.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hybridtheory.mozarella.persistence.repository.StudentRepository;
import com.hybridtheory.mozarella.users.CredentialType;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.users.StudentFactory;
import com.hybridtheory.mozzarella.authentication.JwtUtil;
import com.hybridtheory.mozzarella.authentication.UsernamePasswordDecoder;

@RestController
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentFactory studentFactory;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	PasswordEncoder encoder;
	
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<String> usernamePasswordLogin(@RequestHeader("Authorization") String authHeader) {
    	String[] decoded = UsernamePasswordDecoder.decodeUserNamePassword(authHeader);
    	String username = decoded[0];
    	String password = decoded[1];
    	
    	Student student = null;
    	student = getStudentFromRepo(username, student);
    	
    	if(student==null || !encoder.matches(password, student.getPassword())){
    		return new ResponseEntity<String>("unauthorized access",HttpStatus.UNAUTHORIZED);
    	}
    	
    	return new ResponseEntity<String>(jwtUtil.generateToken(student),HttpStatus.OK);
    }
    
    @RequestMapping(value="/register", method=RequestMethod.POST)
    public ResponseEntity<String> usernamePasswordRegistration(@RequestHeader("Authorization") String authHeader) {
    	String[] decoded = UsernamePasswordDecoder.decodeUserNamePassword(authHeader);
    	String username = decoded[0];
    	String password = decoded[1];
    	
    	Student student = null;
    	student = getStudentFromRepo(username, student);
    	
    	if(student==null){
    		student = studentFactory.createStudent(CredentialType.USERNAMEPASSWORD, username+","+password);
    		studentRepository.save(student);
    	} else {
    		return new ResponseEntity<String>(jwtUtil.generateToken(student),HttpStatus.BAD_REQUEST);
    	}
    	
    	return new ResponseEntity<String>(jwtUtil.generateToken(student),HttpStatus.OK);
    }

	private Student getStudentFromRepo(String username, Student student) {
		List<Student> studentsWithName = studentRepository.findByName(username);
    	if(studentsWithName.size()>0){
    		student = studentsWithName.get(0);
    	}
		return student;
	}
	
    @RequestMapping(value="/usernameavailable", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Iterable<Boolean>> listStudents(@RequestParam("name") Optional<String> username) {
    	LOGGER.info("/usernamefree controller method call"+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
    	Boolean available = true;
    	
    	if(username.isPresent()){
    		available = studentRepository.findByName(username.get()).size() == 0;
    	}
    	
    	List<Boolean> result = new ArrayList<Boolean>();
    	result.add(available);
    	
    	return new ResponseEntity<Iterable<Boolean>>(result, HttpStatus.OK);   		
    }
	
}
