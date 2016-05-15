package com.hybridtheory.mozarella.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozzarella.persistence.StudentRepository;

public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal() + "";
	    String password = authentication.getCredentials() + "";

	    Student student = studentRepository.findByName(username);
	    if (student == null) {
	        throw new BadCredentialsException("1000");
	    }
	    if (!student.isEnabled()) {
	        throw new DisabledException("1001");
	    }
	    if (!encoder.matches(password+student.getSalt(), student.getPassword())) {
	        throw new BadCredentialsException("1000");
	    }


	    return new UsernamePasswordAuthenticationToken(username, password, student.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
