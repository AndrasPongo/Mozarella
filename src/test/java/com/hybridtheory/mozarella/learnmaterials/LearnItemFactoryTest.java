package com.hybridtheory.mozarella.learnmaterials;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class LearnItemFactoryTest {

	@Test
	public void learnItemsCreation_createWord() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		
		//When
		LearnItem learnItem = learnItemFactory.createLearnItem("testword");
		
		//Then
		assertTrue(learnItem instanceof Word);
	}
	
	@Test
	public void learnItemsCreation_createMultiWord() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		
		//When
		LearnItem learnItem = learnItemFactory.createLearnItem("test multi word");
		
		//Then
		assertTrue(learnItem instanceof MultiWord);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsCreation_illegalInputs_onlySpaces() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		
		//When & Then
		learnItemFactory.createLearnItem("   ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void learnItemsCreation_illegalInputs_onlyNumbersAsString() {
		//Given
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		
		//When & Then
		learnItemFactory.createLearnItem("1234");
	}	
}
