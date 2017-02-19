package com.hybridtheory.mozarella.users;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;

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
	public void testStudent_initializePet() {
		//Given
		Student student = new Student();
		
		//When
		student.initializePet("testPet1");
		
		//Then
		assertTrue(student.getPet() != null);
	}
}