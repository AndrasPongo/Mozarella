package com.hybridtheory.mozarella.authorization.aspects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hybridtheory.mozarella.api.LearnItemController;
import com.hybridtheory.mozarella.configuration.authorization.AspectConfiguration;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;
import com.hybridtheory.mozzarella.authentication.JwtUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AspectConfiguration.class, AspectTestConfig.class})
//when(studentRepository.getOwnerOfItemByItemId(0)).thenReturn(0);
public class LearnItemAuthorizationAspectTest {
	
	@Autowired
	private LearnItemAuthorizationAspect learnItemAuthorizationAspect;

	@Autowired
	private LearnItemController learnItemController;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@After
	public void restore(){
		learnItemAuthorizationAspect.setWasCalled(false);
	}
	
	@Test
	public void aspectIsCalled() {
		assertFalse(learnItemAuthorizationAspect.getWasCalled());
		Student student = new Student();
		student.setId(0);
		
		String token = jwtUtil.generateToken(student);
		Optional<String> authToken = Optional.of("Bearer "+token);
		
		learnItemController.deleteItemAuthorized(authToken,0);
		assertTrue(learnItemAuthorizationAspect.getWasCalled());
	}
	
	@Test
	public void authorizedUserGoesThroughDELETE() {
		Student student = new Student();
		student.setId(0);

		LearnItem item = Mockito.mock(LearnItem.class);
		Mockito.when(item.getId()).thenReturn(0);
		
		String token = jwtUtil.generateToken(student);
		
		Optional<String> authHeader = Optional.of("Bearer "+token);
		Object result = learnItemController.deleteItemAuthorized(authHeader, 0);
		
		assertTrue(result!=null);
	}
	
	@Test
	public void nonAuthorizedUserGetsBlockedDELETE() {
		Student student = new Student();
		student.setId(1); //this is not the owner of the given LearnItem, see the config

		LearnItem item = Mockito.mock(LearnItem.class);
		Mockito.when(item.getId()).thenReturn(0);
		
		String token = jwtUtil.generateToken(student);
		
		Optional<String> authHeader = Optional.of("Bearer "+token);
		Object result = learnItemController.deleteItemAuthorized(authHeader, 0);
		
		assertTrue(result==null);
	}
}
