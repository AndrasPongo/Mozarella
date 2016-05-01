package com.hybridtheory.mozarella.pet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class Habitat {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	protected Integer id;
}
