package com.hybridtheory.mozarella.users;

import java.util.List;

import com.hybridtheory.mozarella.pet.Pet;
import com.hybridtheory.mozarella.pet.PetHabitat;
import com.hybridtheory.mozarella.pet.cubefish.Aquarium;
import com.hybridtheory.mozarella.pet.cubefish.CubeFish;
import com.hybridtheory.mozarella.wordteacher.InputSanitizer;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.*;

public class Student {
	private String id = "";
	private String name = "";
	
	private LearnItemsManager ownLearnItemManager = null;
	private Pet ownPet = null;
	private PetHabitat ownPetHabitat = null;
	private InputSanitizer inputSanitizer = new InputSanitizer();
	
	public String getName() {
		return this.name;
	}
	
	public Student(String studentName) {
		initializeStudent(studentName);
	}
	
	private void initializeStudent(String studentName) {
		boolean validName = false;
		validName = inputSanitizer.checkStudentNameIsValid(studentName);
		if (!validName) {
			throw new IllegalArgumentException("Invalid name for Student");
		}
		this.id = "testStudent"+Math.random()*1000;
		this.name = studentName;
		ownLearnItemManager = new LearnItemsManager(this);
	}
	
	protected void initializePet(String petName) {
		ownPet = new CubeFish(petName);
		ownPetHabitat = new Aquarium();	
	}
	
	public Pet getPet() {
		if (ownPet != null) {
			return this.ownPet;
		} else {
			return null;
		}
	}

	protected LearnItemsManager getOwnLearnItemManager() {
		return ownLearnItemManager;
	}
	
	protected List<LearnItemsList> getOwnLearnItemLists() {
		List<LearnItemsList> ownLearnItemsLists = ownLearnItemManager.getLearnItemsLists();
		if (ownLearnItemsLists != null) {
			return ownLearnItemsLists;	
		} else {
			return null;
		}
	}

	protected void addNewLearnItemsList(String name) {
		ownLearnItemManager.createLearnItemsList(name);
	}
}