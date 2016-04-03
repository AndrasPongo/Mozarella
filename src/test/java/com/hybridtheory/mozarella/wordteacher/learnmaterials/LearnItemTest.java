package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LearnItemTest {
	
	@Test
	public void learnItemsList_getTextOfLearnItem() {
		//Given
		LearnItem testLearnItem = new Word("testword", "xyz");

		//When & Then
		assertTrue(testLearnItem.getText() == "testword");
	}
	
	@Test
	public void learnItemsList_setTextOfLearnItem() {
		//Given
		LearnItem testLearnItem = new Word("testword", "xyz");

		//When		
		testLearnItem.setText("testword_new");

		//Then
		assertTrue(testLearnItem.getText() == "testword_new");
	}
	
	@Test
	public void learnItemsList_getTranslationOfLearnItem() {
		//Given
		LearnItem testLearnItem = new Word("testword", "xyz");

		//When & Then
		assertTrue(testLearnItem.getTranslation() == "xyz");
	}
	
	@Test
	public void learnItemsList_setTranslationOfLearnItem() {
		//Given
		LearnItem testLearnItem = new Word("testword", "xyz");

		//When		
		testLearnItem.setTranslation("xyz_new");

		//Then
		assertTrue(testLearnItem.getTranslation()== "xyz_new");
	}
	
	@Test
	public void learnItemsList_setAndGetAlternativeTranslationOfLearnItem() {
		//Given
		LearnItem testLearnItem = new Word("testword", "xyz");

		//When		
		testLearnItem.setAlternativeTranslation("xyz_alternative");

		//Then
		assertTrue(testLearnItem.getAlternativeTranslations().get(0)== "xyz_alternative");
	}
	
	@Test
	public void learnItemsList_removeAlternativeTranslationOfLearnItem() {
		//Given
		LearnItem testLearnItem = new Word("testword", "xyz");
		testLearnItem.setAlternativeTranslation("xyz_alternative");

		//When		
		testLearnItem.removeAlternativeTranslation("xyz_alternative");

		//Then
		assertTrue(testLearnItem.getAlternativeTranslations().size()==0);
	}
	
	//TODO: test for the rest of the setters/getters(?)
	
}
