package com.hybridtheory.mozarella.pet;

import java.time.LocalDate;
import java.util.Map;

public abstract class AbstractPet implements Pet {
	protected String id = "";
	protected String name = "";
	protected int level = 0;
	protected Map extras = null;
	protected LocalDate lastFeedTime = null;
	protected PetHabitat ownHabitat = null;

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public PetHabitat getHabitat() {
		return this.ownHabitat;
	}
	
	@Override
	public void setLevel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addExtra(String type, String subtype) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void removeExtra() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addPoints(int points) {
		// TODO Auto-generated method stub
		
	}

	public void setLastFeedTime(LocalDate lastFeedTime) {	
	}
	
	public LocalDate getLastFeedTime() {
		return null;
	}

	@Override
	public void feed(String typeOfFood, int amountOfFood) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String manifest() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
