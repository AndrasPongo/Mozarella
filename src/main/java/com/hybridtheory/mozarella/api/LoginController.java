package com.hybridtheory.mozarella.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hybridtheory.mozarella.persistence.repository.StudentRepository;
import com.hybridtheory.mozarella.users.CredentialType;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.users.StudentFactory;
import com.hybridtheory.mozzarella.authentication.JwtUtil;

@RestController("/login")
public class LoginController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentFactory studentFactory;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Value("${jwt.secret}") //the same value can be used as for the jwt secret
	String salt;
	
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<String> listStudents(@RequestParam("username") String username, @RequestParam("password") String password) {
    	
    	Student student = studentRepository.findByName(username);
    	if(student==null){
    		student = studentFactory.createStudent(CredentialType.USERNAMEPASSWORD, username+","+password);
    		studentRepository.save(student);
    	} else {
    		student.getPassword().equals(encoder.encode(password+salt));
    	}
    	
    	return new ResponseEntity<String>(jwtUtil.generateToken(student),HttpStatus.OK);
    }
	
}
