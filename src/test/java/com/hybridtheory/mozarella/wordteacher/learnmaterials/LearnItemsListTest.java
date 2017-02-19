package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;

public class LearnItemsListTest {

	@Test
	public void learnItemsList_createWithValidName() {
		//Given
		
		//When
		LearnItemList learnItemList= new LearnItemList("testList1");
		
		//Then
		assertTrue(learnItemList.getName() == "testList1");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsList_createWithEmptyName() {
		//Given
		
		//When & Then
		new LearnItemList("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsList_createWithOnlyWhiteSpacesName() {
		//Given
		
		//When & Then
		new LearnItemList("       ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsList_createWithForbiddenName() {
		//Given
		
		//When & Then
		new LearnItemList("ForbiddenCharSequenceForTestRADNOMlkkbwesadvu84416rk2u3");
	}
	
	@Test
	public void learnItemsList_changeNameOfLearnItemsList() {
		//Given
		LearnItemList learnItemList = new LearnItemList("testList1");
		
		//When
		learnItemList.setName("changedListName1");
		
		//Then
		assertTrue(learnItemList.getName() == "changedListName1");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsList_changeNameOfLearnItemsList_withEmptyString() {
		//Given
		LearnItemList learnItemList = new LearnItemList("testList1");
		
		//When & Then
		learnItemList.setName("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsList_changeNameOfLearnItemsList_withOnlyWhiteSpaces() {
		//Given
		LearnItemList learnItemList = new LearnItemList("     ");
		
		//When & Then
		learnItemList.setName("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsList_changeNameOfLearnItemsList_withForbiddenName() {
		//Given
		LearnItemList learnItemList = new LearnItemList("ForbiddenCharSequenceForTestRADNOMlkkbwesadvu84416rk2u3");
		
		//When & Then
		learnItemList.setName("");
	}
	
	@Test
	public void learnItemsList_addValidLearnItem() {
		//Given
		LearnItemList learnItemList = new LearnItemList("testList1");
		LearnItem testLearnItem = new LearnItem("testword", "xyz");
		Integer originalLearnItemNumber = learnItemList.getNumberOfLearnItemsInList();
		
		//When
		learnItemList.addLearnItem(testLearnItem);
		
		//Then
		assertTrue(learnItemList.getNumberOfLearnItemsInList() == originalLearnItemNumber+1);
	}
	
	@Test
	public void learnItemsList_addAlreadyExistingLearnItem() {
		//Given
		LearnItemList learnItemList = new LearnItemList("testList1");
		LearnItem testLearnItem = new LearnItem("testword", "xyz");
		learnItemList.addLearnItem(testLearnItem);
		int originalLearnItemNumber = learnItemList.getNumberOfLearnItemsInList();
		
		//When
		learnItemList.addLearnItem(testLearnItem);
		
		//Then
		assertTrue(learnItemList.getNumberOfLearnItemsInList() == originalLearnItemNumber);
	}

	@Test
	public void learnItemsList_removeValidLearnItem() {
		//Given
		boolean success = false;
		LearnItemList learnItemList = new LearnItemList("testList1");
		LearnItem testLearnItem = new LearnItem("testword", "xyz");
		learnItemList.addLearnItem(testLearnItem);
		int originalLearnItemNumber = learnItemList.getNumberOfLearnItemsInList();
		
		//When
		success = learnItemList.removeLearnItem(testLearnItem);
		
		//Then
		assertTrue(success == true && learnItemList.getNumberOfLearnItemsInList() == originalLearnItemNumber-1);
	}
	
	@Test
	public void learnItemsList_removeNotExistingLearnItem() {
		//Given
		boolean success = false;
		LearnItemList learnItemList = new LearnItemList("testList1");
		int originalLearnItemNumber = learnItemList.getNumberOfLearnItemsInList();
		
		//When
		success = learnItemList.removeLearnItem(new LearnItem("notexisting", "xyz"));

		//Then
		assertTrue(success == false && learnItemList.getNumberOfLearnItemsInList() == originalLearnItemNumber);
	}
	
}
