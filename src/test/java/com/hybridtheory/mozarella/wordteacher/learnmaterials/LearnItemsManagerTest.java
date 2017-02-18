package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.hybridtheory.mozarella.users.Student;


public class LearnItemsManagerTest {

	@Test
	public void learnItemsManager_initializeWithValidName() {
		//Given
		Student testStudent1 = new Student();
		
		//When
		LearnItemManager learnItemsManager = new LearnItemManager(testStudent1);
		
		//Then
		assertTrue(learnItemsManager.getOwner() == testStudent1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsManager_createWithNullUser() {
		//Given
		
		//When & Then
		new LearnItemManager(null);
	}
	
	@Test
	public void learnItemsManager_createLearnItemsListWithValidName() {
		//Given
		Student testStudent1 = new Student();
		LearnItemManager learnItemsManager = new LearnItemManager(testStudent1);

		//When
		LearnItemList newLearnItemsList = learnItemsManager.createLearnItemsList("testLearnItemsList");

		
		//Then
		assertTrue(learnItemsManager.getLearnItemsLists().contains(newLearnItemsList));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsManager_createLearnItemsListWithNull() {
		//Given
		Student testStudent1 = new Student();
		LearnItemManager learnItemsManager = new LearnItemManager(testStudent1);

		//When & Then
		learnItemsManager.createLearnItemsList(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsManager_createAlreadyExistingLearnItemsList() {
		//Given
		Student testStudent1 = new Student();
		LearnItemManager learnItemsManager = new LearnItemManager(testStudent1);
		learnItemsManager.createLearnItemsList("testLearnItemsList");

		//When & Then
		learnItemsManager.createLearnItemsList("testLearnItemsList");
	}
	
	@Test
	public void learnItemsManager_addNewValidWordToExistingList() {
		//Given
		Student testStudent1 = new Student();
		LearnItemManager learnItemsManager = new LearnItemManager(testStudent1);
		LearnItemList existingLearnItemsList = learnItemsManager.createLearnItemsList("testLearnItemsList");
		
		//When
		learnItemsManager.createNewLearnItemToLearnItemsList(existingLearnItemsList, "testword", "xyz");
		LearnItemList learnItemsList = learnItemsManager.getLearnItemsList("testLearnItemsList");
		
		//Then
		assertTrue(learnItemsList.getNumberOfLearnItemsInList()==1);
	}
	
	@Test
	public void learnItemsManager_addNewValidMultiWordToExistingList() {
		//Given
		Student testStudent1 = new Student();
		LearnItemManager learnItemsManager = new LearnItemManager(testStudent1);
		LearnItemList existingLearnItemsList = learnItemsManager.createLearnItemsList("testLearnItemsList");
		
		//When
		learnItemsManager.createNewLearnItemToLearnItemsList(existingLearnItemsList, "test multi word", "abc xyz");
		LearnItemList learnItemsList = learnItemsManager.getLearnItemsList("testLearnItemsList");
		
		//Then
		assertTrue(learnItemsList.getNumberOfLearnItemsInList()==1);
	}
	
	@Test
	public void learnItemsManager_addNewValidLearnItemToExistingList() {
		//Given
		Student testStudent1 = new Student();
		LearnItemManager learnItemsManager = new LearnItemManager(testStudent1);
		LearnItemList existingLearnItemsList = learnItemsManager.createLearnItemsList("testLearnItemsList");

		LearnItem learnItem = new LearnItem("testword", "xyz");
		
		//When
		learnItemsManager.addNewLearnItemToLearnItemsList(existingLearnItemsList, learnItem);
		
		//Then
		assertTrue(existingLearnItemsList.getNumberOfLearnItemsInList()==1);
	}
	
	@Test
	public void learnItemsManager_getValidWordFromExistingList() {
		//Given
		Student testStudent1 = new Student();
		LearnItemManager learnItemsManager = new LearnItemManager(testStudent1);
		LearnItemList existingLearnItemsList = learnItemsManager.createLearnItemsList("testLearnItemsList");
		learnItemsManager.createNewLearnItemToLearnItemsList(existingLearnItemsList, "testword", "xyz");

		//When
		LearnItem learnItem = learnItemsManager.getLearnItemFromList(existingLearnItemsList, "testword");
		
		//Then
		assertTrue(learnItem.getText().equals("testword") && learnItem.getTranslation().equals("xyz"));
	}
	
	/* Getting a learnItem from a list is not the learnItemsManager's responsibility
	 * 
	 * @Test
	public void learnItemsManager_getValidMultiWordFromExistingList() {
		//Given
		Student testStudent1 = new Student();
		LearnItemsManager learnItemsManager = new LearnItemsManager(testStudent1);
		LearnItemList existingLearnItemsList = learnItemsManager.createLearnItemsList("testLearnItemsList");
		learnItemsManager.createNewLearnItemToLearnItemsList(existingLearnItemsList, "test multi word", "abc xyz");

		//When
		LearnItem learnItem = learnItemsManager.getLearnItemFromList(existingLearnItemsList, "test multi word");
		
		//Then
		assertTrue(learnItem.getText(),equals("test multi word") && learnItem.getTranslation().equals("abc xyz"));
	}*/
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsManager_addNewValidWordToNotExistingList() {
		//Given
		Student testStudent1 = new Student();
		LearnItemManager learnItemsManager = new LearnItemManager(testStudent1);
		
		//When & Then
		learnItemsManager.createNewLearnItemToLearnItemsList(null, "testword", "xyz");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsManager_addNewValidMultiWordToNotExistingList() {
		//Given
		Student testStudent1 = new Student();
		LearnItemManager learnItemsManager = new LearnItemManager(testStudent1);
		
		//When & Then
		learnItemsManager.createNewLearnItemToLearnItemsList(null, "test multi word", "abc xyz");
	}
	
	@Test
	public void learnItemsManager_removeValidWordFromValidList() {
		//Given
		Student testStudent1 = new Student();
		LearnItemManager learnItemsManager = new LearnItemManager(testStudent1);
		LearnItemList existingLearnItemsList = learnItemsManager.createLearnItemsList("testLearnItemsList");
		learnItemsManager.createNewLearnItemToLearnItemsList(existingLearnItemsList, "testword", "xyz");
		LearnItem learnItem = learnItemsManager.getLearnItemFromList(existingLearnItemsList, "testword");
		
		//When
		learnItemsManager.removeLearnItemFromLearnItemsList(existingLearnItemsList, learnItem);
		
		//Then
		assertTrue(existingLearnItemsList.getNumberOfLearnItemsInList()==0);
	}
	
	@Test
	public void learnItemsManager_setStrengthOfLearnItem_valid() {
		//Given
		Student testStudent1 = new Student();
		LearnItemManager learnItemsManager = new LearnItemManager(testStudent1);
		LearnItemList existingLearnItemsList = learnItemsManager.createLearnItemsList("testLearnItemsList");
		learnItemsManager.createNewLearnItemToLearnItemsList(existingLearnItemsList, "testword", "xyz");
		LearnItem learnItem = learnItemsManager.getLearnItemFromList(existingLearnItemsList, "testword");

		//When
		learnItemsManager.setStrengthOfLearnItem(learnItem, 1);
		
		//Then
		assertTrue(learnItem.getStrength()==1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsManager_setStrengthOfLearnItem_invalid() {
		//Given
		Student testStudent1 = new Student();
		LearnItemManager learnItemsManager = new LearnItemManager(testStudent1);
		LearnItemList existingLearnItemsList = learnItemsManager.createLearnItemsList("testLearnItemsList");
		learnItemsManager.createNewLearnItemToLearnItemsList(existingLearnItemsList, "testword", "xyz");
		LearnItem learnItem = learnItemsManager.getLearnItemFromList(existingLearnItemsList, "testword");

		//When & Then
		learnItemsManager.setStrengthOfLearnItem(learnItem, 11);
	}
	
	/* this would be an integration test, not a unit test 
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsToPracticeAreOrderedByPriority() {
		
	} */
}
