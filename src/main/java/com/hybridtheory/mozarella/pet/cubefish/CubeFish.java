package com.hybridtheory.mozarella.pet.cubefish;

import java.time.LocalDate;
import com.hybridtheory.mozarella.pet.AbstractPet;
import com.hybridtheory.mozarella.wordteacher.InputSanitizer;

public class CubeFish extends AbstractPet {
	private InputSanitizer inputSanitizer = new InputSanitizer();

	public CubeFish(String petName) {
		boolean validName = inputSanitizer.checkPetNameIsValid(petName);
		
		if (!validName) {
			throw new IllegalArgumentException("Invalid name for the Pet");
		} else {
			this.id = "testCubeFish"+Math.random()*1000;
			this.name = petName;
			this.setLastFeedTime(LocalDate.now());
			this.ownHabitat = new Aquarium();		
		}
	}
}
