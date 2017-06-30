package com.hybridtheory.mozarella.authorization.aspects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.hybridtheory.mozarella.api.LearnItemListController;
import com.hybridtheory.mozarella.configuration.authorization.AspectConfiguration;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;
import com.hybridtheory.mozzarella.authentication.JwtUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AspectConfiguration.class, AspectTestConfig.class}, loader = AnnotationConfigContextLoader.class)
//the mock learnItemListRepository is configured so that the owner of list 0 is student 0, and the owner of list 1 is student 1
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
		Optional<String> authToken = Optional.of("Bearer "+token);
		
		learnItemListController.getLearnItemListAuthorized(authToken,0);
		assertTrue(learnItemListAuthorizationAspect.getWasCalled());
	}
	
	@Test
	public void authorizedUserGoesThroughPOST() {
		Student student = new Student();
		student.setId(0);
		
		LearnItemList listToPersist = Mockito.mock(LearnItemList.class);
		Mockito.when(listToPersist.getId()).thenReturn(0);
		
		String token = jwtUtil.generateToken(student);
		
		Optional<String> authHeader = Optional.of("Bearer "+token);
		Object result = learnItemListController.saveLearnItemListAuthorized(authHeader, listToPersist);
		
		assertTrue(result!=null);
	}
	
	@Test
	public void nonAuthorizedUserGetsBlockedPOST() {
		Student student = new Student();
		student.setId(0);
		
		LearnItemList listToPersist = Mockito.mock(LearnItemList.class);
		Mockito.when(listToPersist.getId()).thenReturn(1); //we're trying to access the learnItemList with id 1, which is not associated with user 0
		
		String token = jwtUtil.generateToken(student);
		
		Optional<String> authHeader = Optional.of("Bearer "+token);
		Object result = learnItemListController.saveLearnItemListAuthorized(authHeader, listToPersist);
		
		assertTrue(result==null);
	}
	
	/*
	 * TODO later, when get learnitemlist authorization is implemented
	 * @Test
	public void authorizedUserGoesThroughGET() {
		Student student = new Student();
		student.setId(0);
		
		String token = jwtUtil.generateToken(student);
		
		Optional<String> authToken = Optional.of(token);
		Object result = learnItemListController.getLearnItemListAuthorized(authToken,0);
		
		assertTrue(result!=null);
	}*/
	
	/*
	 * TODO later, when get learnitemlist authorization is implemented
	@Test
	public void nonAuthorizedUserGetsBlockedGET() {
		Student student = new Student();
		student.setId(0);
		
		String token = jwtUtil.generateToken(student);
		
		Optional<String> authToken = Optional.of(token);
		Object result = learnItemListController.getLearnItemListAuthorized(authToken,1);
		
		assertTrue(result==null);
	}*/
	
	

}
