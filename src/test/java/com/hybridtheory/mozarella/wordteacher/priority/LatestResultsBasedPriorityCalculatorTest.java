package com.hybridtheory.mozarella.wordteacher.priority;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

import com.hybridtheory.mozarella.persistence.repository.ResultRepository;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;

@RunWith(MockitoJUnitRunner.class)
public class LatestResultsBasedPriorityCalculatorTest {
	
	//private static Boolean setupIsDone = false;
	
	@InjectMocks
	PriorityCalculator priorityCalculator = new LatestResultsBasedPriorityCalculator(4);

	private static List<Result> results;
	private static Student student;
	private static LearnItem learnItem;
	private static ResultRepository resultRepository;
	
	@BeforeClass
	public static void setUp() {

		List<Result> results = new ArrayList<Result>();
		
		student = mock(Student.class);
		when(student.getId()).thenReturn(42);
		
		learnItem = mock(LearnItem.class);
		when(learnItem.getId()).thenReturn(42);
		
		resultRepository = mock(ResultRepository.class);
		when(resultRepository.getLastResultsForStudentAndLearnItem(org.mockito.Matchers.any(Integer.class), org.mockito.Matchers.any(Integer.class), org.mockito.Matchers.any(PageRequest.class))).thenReturn(results);
	    
	}
	
	@Test
	public void test_returns_positive_priority() {
		
		//when
		results = new ArrayList<Result>();
		results.add(new Result(false,student,learnItem));
		results.add(new Result(true,student,learnItem));
		results.add(new Result(false,student,learnItem));
		results.add(new Result(true,student,learnItem));
		results.add(new Result(false,student,learnItem));
		
		when(resultRepository.getLastResultsForStudentAndLearnItem(org.mockito.Matchers.any(Integer.class), org.mockito.Matchers.any(Integer.class), org.mockito.Matchers.any(PageRequest.class))).thenReturn(results);
		
		Double priority = priorityCalculator.calculatePriority(student, learnItem);
		
		//then
		assertThat("priority",
		           priority,
		           greaterThan(0.0));
	}
	
	@Test
	public void test_more_successful_item_has_less_priority() {
		
		//when
		results = new ArrayList<Result>();
		results.add(new Result(false,student,learnItem));
		results.add(new Result(false,student,learnItem));
		results.add(new Result(false,student,learnItem));
		results.add(new Result(false,student,learnItem));
		results.add(new Result(false,student,learnItem));
		
		when(resultRepository.getLastResultsForStudentAndLearnItem(org.mockito.Matchers.any(Integer.class), org.mockito.Matchers.any(Integer.class), org.mockito.Matchers.any(PageRequest.class))).thenReturn(results);
		
		Double priority1 = priorityCalculator.calculatePriority(student, learnItem);
		
		results = new ArrayList<Result>();
		results.add(new Result(false,student,learnItem));
		results.add(new Result(true,student,learnItem));
		results.add(new Result(false,student,learnItem));
		results.add(new Result(false,student,learnItem));
		results.add(new Result(false,student,learnItem));
		
		when(resultRepository.getLastResultsForStudentAndLearnItem(org.mockito.Matchers.any(Integer.class), org.mockito.Matchers.any(Integer.class), org.mockito.Matchers.any(PageRequest.class))).thenReturn(results);
		
		Double priority2 = priorityCalculator.calculatePriority(student, learnItem);
		
		assertThat("priority1 is bigger than priority2",
		           priority1,
		           greaterThan(priority2));
		
	}
	
	@Test
	public void latest_result_has_higher_priority() {
		//when
		results = new ArrayList<Result>();
		results.add(new Result(true,student,learnItem));
		results.add(new Result(false,student,learnItem));
		results.add(new Result(false,student,learnItem));
		results.add(new Result(false,student,learnItem));
		results.add(new Result(false,student,learnItem));
		
		when(resultRepository.getLastResultsForStudentAndLearnItem(org.mockito.Matchers.any(Integer.class), org.mockito.Matchers.any(Integer.class), org.mockito.Matchers.any(PageRequest.class))).thenReturn(results);
		
		Double priority1 = priorityCalculator.calculatePriority(student, learnItem);
		
		results = new ArrayList<Result>();
		results.add(new Result(false,student,learnItem));
		results.add(new Result(true,student,learnItem));
		results.add(new Result(false,student,learnItem));
		results.add(new Result(false,student,learnItem));
		results.add(new Result(false,student,learnItem));
		
		when(resultRepository.getLastResultsForStudentAndLearnItem(org.mockito.Matchers.any(Integer.class), org.mockito.Matchers.any(Integer.class), org.mockito.Matchers.any(PageRequest.class))).thenReturn(results);
		
		Double priority2 = priorityCalculator.calculatePriority(student, learnItem);
		
		assertThat("priority 1 is less than priority2",
		           priority1,
		           lessThan(priority2));
		
		assertTrue(priority1<priority2);
	}

}
