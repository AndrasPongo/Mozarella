package com.hybridtheory.mozarella.users;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemsList;

public class StudentTest {

	@Test
	public void testStudent_correctInputs() {
		//Given
	
		//When
		Student student = new Student();
		student.setName("testStudent1");
				
		//Then
		assertTrue(student.getName().equals("testStudent1"));
	}	

	@Test
	public void testStudent_learnItemsManagerCreation() {
		//Given
	
		//When
		Student student = new Student();
				
		//Then
		assertTrue(student.getOwnLearnItemManager() != null);
	}
	
	@Test
	public void testStudent_addAndGetNewLearnItemsList() {
		//Given
		LearnItemsList learnItemsList = new LearnItemsList("testLearnItemList");
		//When
		Student student = new Student();
		student.addNewLearnItemsList(learnItemsList);
				
		//Then
		assertTrue(student.getOwnLearnItemLists().contains(learnItemsList));
	}
	
	@Test
	public void testStudent_initializePet() {
		//Given
		Student student = new Student();
		
		//When
		student.initializePet("testPet1");
		
		//Then
		assertTrue(student.getPet() != null);
	}
}