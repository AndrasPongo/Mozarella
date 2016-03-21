package com.hybridtheory.mozarella.pet;

import java.time.LocalDate;

public interface Pet {

	public void setName(String name);
	public String getName();
	
	public PetHabitat getHabitat();
	
	public void setLevel();
	
	public void addExtra(String type, String subtype);
	public void removeExtra();
	
	public void addPoints(int points);
	
	public void setLastFeedTime(LocalDate lastFeedTime);
	public LocalDate getLastFeedTime();
	
	public void feed(String typeOfFood, int amountOfFood);

	public String manifest();
	
	
	
}
