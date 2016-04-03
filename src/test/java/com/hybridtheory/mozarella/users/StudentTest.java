package com.hybridtheory.mozarella.users;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemFactory;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemsList;

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
	public void testStudent_addAndGetNewLearnItemToExistingList() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		LearnItem learnItem = learnItemFactory.createLearnItem("testword", "xyz");
	
		//When
		Student student = new Student("testStudent1");
		LearnItemsList learnItemsList = student.addNewLearnItemsList("testList1");
		student.addNewLearnItemToExistingList(learnItemsList, learnItem);
				
		//Then
		assertTrue(student.getALearnItemList("testList1") != null);
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