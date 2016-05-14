package com.hybridtheory.mozzarella.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/login")
public class LoginController {

	@Autowired
	AuthenticationManager authenticationManager;
	
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<String> listStudents(@RequestParam("username") String username, @RequestParam("password") String password) {
    	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    	
    	return new ResponseEntity<String>(HttpStatus.OK);
    }
	
}
