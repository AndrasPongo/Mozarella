package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LearnItemTest {
	
	@Test
	public void learnItemsList_getTextOfLearnItem() {
		//Given
		LearnItem testLearnItem = new LearnItem("testword", "xyz");

		//When & Then
		assertTrue(testLearnItem.getText().equals("testword"));
	}
	
	@Test
	public void learnItemsList_setTextOfLearnItem() {
		//Given
		LearnItem testLearnItem = new LearnItem("testword", "xyz");

		//When		
		testLearnItem.setText("testword_new");

		//Then
		assertTrue(testLearnItem.getText() == "testword_new");
	}
	
	@Test
	public void learnItemsList_getTranslationOfLearnItem() {
		//Given
		LearnItem testLearnItem = new LearnItem("testword", "xyz");

		//When & Then
		assertTrue(testLearnItem.getTranslation().equals("xyz"));
	}
	
	@Test
	public void learnItemsList_setTranslationOfLearnItem() {
		//Given
		LearnItem testLearnItem = new LearnItem("testword", "xyz");

		//When		
		testLearnItem.addTranslation("xyz_new");

		//Then
		assertTrue(testLearnItem.getTranslations().contains("xyz_new"));
	}
	
	@Test
	public void learnItemsList_setAndGetAlternativeTranslationOfLearnItem() {
		//Given
		LearnItem testLearnItem = new LearnItem("testword", "xyz");

		//When		
		testLearnItem.addTranslation("xyz_alternative");

		//Then
		assertTrue(testLearnItem.getTranslation(1).equals("xyz_alternative"));
	}
	
	/*@Test
	public void learnItemsList_removeAlternativeTranslationOfLearnItem() {
		//Given
		LearnItem testLearnItem = new Word("testword", "xyz");
		testLearnItem.addTranslation("xyz_alternative");

		//When		
		testLearnItem.removeAlternativeTranslation("xyz_alternative");

		//Then
		assertTrue(testLearnItem.getTranslations().size()==0);
	}
	
	//TODO: test for the rest of the setters/getters(?) */
	
}
