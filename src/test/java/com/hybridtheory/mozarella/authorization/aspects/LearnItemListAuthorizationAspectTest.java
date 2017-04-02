package com.hybridtheory.mozarella.authorization.aspects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hybridtheory.mozarella.api.LearnItemListController;
import com.hybridtheory.mozarella.configuration.general.AspectConfiguration;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozzarella.authentication.JwtUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AspectConfiguration.class, AspectTestConfig.class})
public class LearnItemListAuthorizationAspectTest {

	@Autowired
	private LearnItemListAuthorizationAspect learnItemListAuthorizationAspect;

	@Autowired
	private LearnItemListController learnItemListController;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@After
	public void restore(){
		learnItemListAuthorizationAspect.setWasCalled(false);
	}
	
	@Test
	public void aspectIsCalled() {
		assertFalse(learnItemListAuthorizationAspect.getWasCalled());
		Student student = new Student();
		student.setId(0);
		
		String token = jwtUtil.generateToken(student);
		Optional<String> authToken = Optional.of(token);
		
		learnItemListController.getLearnItemListAuthorized(authToken,0);
		assertTrue(learnItemListAuthorizationAspect.getWasCalled());
	}
	
	@Test
	public void authorizedUserGoesThrough() {
		Student student = new Student();
		student.setId(0);
		
		String token = jwtUtil.generateToken(student);
		
		Optional<String> authToken = Optional.of(token);
		Object result = learnItemListController.getLearnItemListAuthorized(authToken,0);
		
		assertTrue(result!=null);
	}
	
	@Test
	public void nonAuthorizedUserGetsBlocked() {
		Student student = new Student();
		student.setId(0);
		
		String token = jwtUtil.generateToken(student);
		
		Optional<String> authToken = Optional.of(token);
		Object result = learnItemListController.getLearnItemListAuthorized(authToken,1);
		
		assertTrue(result==null);
	}

}
