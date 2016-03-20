package com.hybridtheory.mozarella.users;

import java.util.*;

import com.hybridtheory.mozarella.wordteacher.InputSanitizer;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemsList;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemsManager;

public class Student {
	private String id = "";
	private String name = "";
	
	private LearnItemsManager ownLearnItemManager = null;
	private InputSanitizer inputSanitizer = new InputSanitizer();
	
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

	protected LearnItemsManager getOwnLearnItemManager() {
		return ownLearnItemManager;
	}

	protected void addNewLearnItemsList(String name) {
		ownLearnItemManager.createLearnItemsList(name);
		//ownedLearnItemsLists.add(learnItemsList);
	}

	protected String[] getExistingListNames() {
		List<LearnItemsList> ownedLearnItemsLists = ownLearnItemManager.getLearnItemsLists();
		String[] ownedLearnItemsListNames = new String[ownedLearnItemsLists.size()];
		//TODO: Java8
		int i = 0;
		for (LearnItemsList learnItemsList : ownedLearnItemsLists) {
			ownedLearnItemsListNames[i] = learnItemsList.getName();
			i++;
		}
		return ownedLearnItemsListNames;
	}
}
