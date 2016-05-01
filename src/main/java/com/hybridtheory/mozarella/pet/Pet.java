package com.hybridtheory.mozarella.pet;

import java.time.LocalDate;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.hybridtheory.mozarella.pet.cubefish.Aquarium;

@Entity
public abstract class Pet {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	protected Integer id;
	protected String name = "";
	protected Integer level = 0;
	@ElementCollection
	protected Map<Integer, String> extras = null;
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
