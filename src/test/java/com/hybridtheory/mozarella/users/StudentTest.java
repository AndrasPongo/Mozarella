package com.hybridtheory.mozarella.users;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class StudentTest {

	@Test
	public void testStudent_correctInputs() {
		//Given
	
		//When
		Student student = new Student("testStudent1");
				
		//Then
		assertTrue(student.getName()=="testStudent1");
	}	

	@Test
	public void testStudent_learnItemsManagerCreation() {
		//Given
	
		//When
		Student student = new Student("testStudent1");
				
		//Then
		assertTrue(student.getOwnLearnItemManager() != null);
	}
	
	@Test
	public void testStudent_addAndGetNewLearnItemsList() {
		//Given
	
		//When
		Student student = new Student("testStudent1");
		student.addNewLearnItemsList("testList1");
				
		//Then
		assertTrue(student.getOwnLearnItemLists() != null);
	}
	
	@Test
	public void testStudent_initializePet() {
		//Given
		Student student = new Student("testStudent1");
		
		//When
		student.initializePet("testPet1");
		
		//Then
		assertTrue(student.getPet() != null);
	}
}