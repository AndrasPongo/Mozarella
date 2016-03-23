package com.hybridtheory.mozarella.pet;

import java.time.LocalDate;
import java.util.Map;

import javax.persistence.OneToOne;

import com.hybridtheory.mozarella.pet.cubefish.Aquarium;


public abstract class Pet {

	protected String name = "";
	protected int level = 0;
	protected Map extras = null;
	protected LocalDate lastFeedTime = null;
	
	@OneToOne(targetEntity=Aquarium.class)
	protected Habitat ownHabitat = null;

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Habitat getHabitat() {
		return this.ownHabitat;
	}
	
	
	public void setLevel() {
		// TODO Auto-generated method stub

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

	public void setLastFeedTime(LocalDate lastFeedTime) {	
	}
	
	public LocalDate getLastFeedTime() {
		return null;
	}

	
	public void feed(String typeOfFood, int amountOfFood) {
		// TODO Auto-generated method stub
		
	}

	
	public String manifest() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
