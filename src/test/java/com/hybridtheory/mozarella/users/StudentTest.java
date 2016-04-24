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
		assertTrue(student.getLearnItemManager() != null);
	}
	
	@Test
	public void testStudent_addAndGetNewLearnItemsList() {
		//Given
		LearnItemsList learnItemsList = new LearnItemsList("testLearnItemList");
		//When
		Student student = new Student();
		student.addNewLearnItemsList(learnItemsList);
				
		//Then
		assertTrue(student.getLearnItemLists().contains(learnItemsList));
	}
	
	@Test
	public void testStudent_addAndGetNewLearnItemToExistingList() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		LearnItem learnItem = learnItemFactory.createLearnItem("testword", "xyz");
	
		//When
		Student student = new Student();
		LearnItemsList learnItemsList = student.addNewLearnItemsList("testList1");
		student.addNewLearnItemToExistingList(learnItemsList, learnItem);
				
		//Then
		assertTrue(student.getLearnItemListByName("testList1") != null);
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