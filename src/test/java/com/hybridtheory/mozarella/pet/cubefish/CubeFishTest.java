package com.hybridtheory.mozarella.pet.cubefish;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hybridtheory.mozarella.pet.Pet;

public class CubeFishTest {

	@Test
	public void testCubeFish_initialize() {
		//Given
	
		//When
		Pet testCubeFish = new CubeFish("testCubeFish1");
			
		//Then
		assertTrue(testCubeFish.getName()=="testCubeFish1" && 
				testCubeFish.getHabitat() != null 
//TODO:			&& testCubeFish.getLastFeedTime().isEqual(LocalDate.now())
				);
	}
	
	//TODO: add negative tests for initialization
	//TODO: add all further tests
}