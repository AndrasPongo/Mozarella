package com.hybridtheory.mozarella.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StudentFactory {
	
	@Autowired
	PasswordEncoder encoder;
	
	@Value("${jwt.secret}") //the same value can be used as for the jwt secret
	String salt;
	
	public Student createStudent(CredentialType credentialType, String... values){
		
		Student newStudent = new Student();
		
		if(credentialType.equals(CredentialType.FACEBOOK)){
			//TODO set facebookid with value
			//TODO: come up with a way to generate a name for the user
		} else if(credentialType.equals(CredentialType.GOOGLE)){
			//TODO set googleid with value
			//TODO: come up with a way to generate a name for the user
		}
		
		return newStudent;
	}
	
	public Student createStudent(Student student){
		return giveHashedPasswordForStudent(student, student.getPassword());
	}
	
	private Student giveHashedPasswordForStudent(Student student, String password){
		String hashedPassword = encoder.encode(password);
		student.setPassword(hashedPassword);
		
		return student;
	}
}
