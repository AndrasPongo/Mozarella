package com.hybridtheory.mozarella.pet.cubefish;

import javax.persistence.Entity;

import com.hybridtheory.mozarella.pet.Pet;

@Entity
public class CubeFish extends Pet {
	
	public CubeFish(String petName) {
		super(petName);
	}
}
