package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemFactory;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.MultiWord;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Word;

public class LearnItemFactoryTest {

	@Test
	public void learnItemsCreation_createWord() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		
		//When
		LearnItem learnItem = learnItemFactory.createLearnItem("testword", "xyz");
		
		//Then
		assertTrue(learnItem instanceof Word);
	}
	
	@Test
	public void learnItemsCreation_createMultiWord() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		
		//When
		LearnItem learnItem = learnItemFactory.createLearnItem("test multi word", "abc xyz");
		
		//Then
		assertTrue(learnItem instanceof MultiWord);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsCreation_illegalInputs_textIsOnlySpaces() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		
		//When & Then
		learnItemFactory.createLearnItem("   ", "xyz");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsCreation_illegalInputs_translationIsOnlySpaces() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		
		//When & Then
		learnItemFactory.createLearnItem("testword", " ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsCreation_illegalInputs_textAndTranslationAreOnlySpaces() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		
		//When & Then
		learnItemFactory.createLearnItem("       ", " ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsCreation_illegalInputs_textIsOnlyNumbers() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		
		//When & Then
		learnItemFactory.createLearnItem("1234", "xyz");
	}	
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsCreation_illegalInputs_translationIsOnlyNumbers() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		
		//When & Then
		learnItemFactory.createLearnItem("testword", "123");
	}	
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsCreation_illegalInputs_textAndTranslationAreOnlyNumbers() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		
		//When & Then
		learnItemFactory.createLearnItem("1234", "123");
	}	
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsCreation_illegalInputs_multiWord_textAndTranslationAreOnlyNumbers() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		
		//When & Then
		learnItemFactory.createLearnItem("1234 5678", "123 456");
	}	
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsCreation_illegalInputs_multiWord_textIsOnlyNumbers() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		
		//When & Then
		learnItemFactory.createLearnItem("1234 5678", "abc xyz");
	}	
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsCreation_illegalInputs_multiWord_translationIsOnlyNumbers() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		
		//When & Then
		learnItemFactory.createLearnItem("test multi word", "123 456");
	}	
	
}
