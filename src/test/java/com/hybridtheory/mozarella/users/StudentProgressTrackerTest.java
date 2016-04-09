package com.hybridtheory.mozarella.users;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemFactory;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemsList;

public class StudentProgressTrackerTest {
	
	@Test
	public void testInit_correctInputs() {
		//Given
		Student student = new Student();
			
		//When
		StudentProgressTracker studentProgressTracker = new StudentProgressTracker(student);

				
		//Then
		assertTrue(studentProgressTracker.getOwner().getName()=="testStudent1");
	}	
	
	@Test(expected = IllegalArgumentException.class)
	public void testInit_incorrectInputs() {
		//Given
			
		//When & Then
		new StudentProgressTracker(null);
	}	
	
	@Test
	public void testLearntCount_correctInputs_noLearntWords() {
		//Given
		Student student = new Student();
		LearnItemsList learnItemsList = student.addNewLearnItemsList("testLearnItemsList");
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		LearnItem learnItem = learnItemFactory.createLearnItem("testword", "xyz");
		student.addNewLearnItemToExistingList(learnItemsList, learnItem);
		
		StudentProgressTracker studentProgressTracker = new StudentProgressTracker(student);

		//When
		int learntItemsCount = studentProgressTracker.returnLearntWordsCountOfStudent();
		System.out.println(learntItemsCount);
				
		//Then
		assertTrue(learntItemsCount==0);
	}

	@Test
	public void testLearntCount_correctInputs_withLearntWord() {
		//Given
		Student student = new Student();
		LearnItemsList learnItemsList = student.addNewLearnItemsList("testLearnItemsList");
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		LearnItem learnItem = learnItemFactory.createLearnItem("testword", "xyz");
		student.addNewLearnItemToExistingList(learnItemsList, learnItem);
		student.getALearnItemList("testLearnItemsList").getLearnItem("testword").setStrength(1);
		
		StudentProgressTracker studentProgressTracker = new StudentProgressTracker(student);

		//When
		int learntItemsCount = studentProgressTracker.returnLearntWordsCountOfStudent();

		//Then
		assertTrue(learntItemsCount==1);
	}
}
