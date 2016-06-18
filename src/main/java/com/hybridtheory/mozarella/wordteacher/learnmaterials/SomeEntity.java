package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SomeEntity {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	String somePropery;

	public String getSomePropery() {
		return somePropery;
	}

	public void setSomePropery(String somePropery) {
		this.somePropery = somePropery;
	}
	
}
