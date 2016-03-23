package com.hybridtheory.mozarella.pet.cubefish;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hybridtheory.mozarella.pet.Habitat;

@Entity
public class Aquarium extends Habitat {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
}
