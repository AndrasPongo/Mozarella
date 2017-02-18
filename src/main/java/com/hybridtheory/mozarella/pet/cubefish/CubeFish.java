package com.hybridtheory.mozarella.pet.cubefish;

import java.io.Serializable;

import javax.persistence.Entity;

import com.hybridtheory.mozarella.pet.Pet;

@Entity
public class CubeFish extends Pet implements Serializable {
	
	private static final long serialVersionUID = 7526471111622776147L;
	
	public CubeFish(){
		super();
	}
	
	public CubeFish(String petName) {
		super(petName);
	}
}
