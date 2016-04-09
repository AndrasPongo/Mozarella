package com.hybridtheory.mozarella.pet.cubefish;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.hybridtheory.mozarella.pet.Pet;
import com.hybridtheory.mozarella.wordteacher.InputSanitizer;

@Entity
public class CubeFish extends Pet {
	//@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	//private Integer id2;
	
	public CubeFish(){
		level = 1;
	}
	
	@Transient
	private InputSanitizer inputSanitizer = new InputSanitizer();

	public CubeFish(String petName) {
		boolean validName = inputSanitizer.checkPetNameIsValid(petName);
		
		if (!validName) {
			throw new IllegalArgumentException("Invalid name for the Pet");
		} else {
			this.petname = petName;
			this.setLastFeedTime(LocalDate.now());
			this.ownHabitat = new Aquarium();		
		}
	}
}
