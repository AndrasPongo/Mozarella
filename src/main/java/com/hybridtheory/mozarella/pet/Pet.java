package com.hybridtheory.mozarella.pet;

import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.hybridtheory.mozarella.wordteacher.InputSanitizer;

@Entity
public abstract class Pet {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	protected Integer id;
	protected Integer xp;
	protected Integer level = 0;
	
	protected String name;
	
	@Transient
	private InputSanitizer inputSanitizer = new InputSanitizer();
	
	protected Pet(){
		
	}
	
	protected Pet(String petName){
		boolean validName = inputSanitizer.checkPetNameIsValid(petName);
		
		if (!validName) {
			throw new IllegalArgumentException("Invalid name for the Pet");
		} else {
			this.name = petName;
		}
	}
	
	@ElementCollection
	protected Map<Integer, String> extras = null;

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setLevel() {
		// TODO Auto-generated method stub
		level=0;

	}

	
	public void addExtra(String type, String subtype) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void removeExtra() {
		// TODO Auto-generated method stub
		
	}
	
	
	public void addPoints(int points) {
		// TODO Auto-generated method stub
		
	}

	
	public void feed(String typeOfFood, int amountOfFood) {
		// TODO Auto-generated method stub
		
	}

	
	public String manifest() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
