package com.hybridtheory.mozarella.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hybridtheory.mozarella.persistence.repository.StudentRepository;
import com.hybridtheory.mozarella.users.CredentialType;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.users.StudentFactory;
import com.hybridtheory.mozzarella.authentication.JwtUtil;
import com.hybridtheory.mozzarella.authentication.UsernamePasswordDecoder;

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
    public ResponseEntity<String> usernamePasswordLogin(@RequestHeader("Authorization") String authHeader) {
    	String[] decoded = UsernamePasswordDecoder.decodeUserNamePassword(authHeader);
    	String username = decoded[0];
    	String password = decoded[1];
    	
    	Student student = studentRepository.findByName(username);
    	if(student==null || !student.getPassword().equals(encoder.encode(password+salt))){
    		return new ResponseEntity<String>("unauthorized access",HttpStatus.UNAUTHORIZED);
    	}
    	
    	return new ResponseEntity<String>("successful authentication",HttpStatus.OK);
    }
    
    @RequestMapping(value="/register", method=RequestMethod.POST)
    public ResponseEntity<String> usernamePasswordRegistration(@RequestHeader("Authorization") String authHeader) {
    	String[] decoded = UsernamePasswordDecoder.decodeUserNamePassword(authHeader);
    	String username = decoded[0];
    	String password = decoded[1];
    	
    	Student student = studentRepository.findByName(username);
    	
    	if(student==null){
    		student = studentFactory.createStudent(CredentialType.USERNAMEPASSWORD, username+","+password);
    		studentRepository.save(student);
    	} else {
    		return new ResponseEntity<String>(jwtUtil.generateToken(student),HttpStatus.BAD_REQUEST);
    	}
    	
    	return new ResponseEntity<String>(jwtUtil.generateToken(student),HttpStatus.OK);
    }
	
}
