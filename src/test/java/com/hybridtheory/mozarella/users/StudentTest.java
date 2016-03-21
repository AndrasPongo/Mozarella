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
	
	@Test(expected = IllegalArgumentException.class)
	public void testStudent_incorrectInputs_onlyWhiteSpaces() {
		//Given
	
		//When & Then
		new Student("  ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testStudent_incorrectInputs_emptyString() {
		//Given
	
		//When & Then
		new Student("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testStudent_incorrectInputs_null() {
		//Given
	
		//When & Then
		new Student(null);
	}	
	
	@Test(expected = IllegalArgumentException.class)
	public void testStudent_incorrectInputs_forbiddenWord() {
		//Given
	
		//When & Then
		new Student("ForbiddenCharSequenceForTestRADNOMlkkbwesadvu84416rk2u3");
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
	public void testStudent_addNewLearnItemsList() {
		//Given
	
		//When
		Student student = new Student("testStudent1");
		student.addNewLearnItemsList("testList1");
				
		//Then
		assertTrue(student.getOwnLearnItemLists() != null);
	}
}