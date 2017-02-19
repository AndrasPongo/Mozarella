package com.hybridtheory.mozarella.users;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;

//TODO: Use a statistics class instead. The stats should be updated in a separate thread when results are returned (e.g. newResultsEvent)
public class StudentProgressTracker {
	private Student owner;
	
	public StudentProgressTracker(Student student) {
		if (student != null) {
			this.owner = student;
		} else {
			throw new IllegalArgumentException("Invalid input when creating Student Progress Tracker");
		}
	}
	
	public Student getOwner() {
		return this.owner;
	}
	
	//TODO: repositories
	public int returnWordsOfCertainStrength(LearnItemList learnItemsList, int strength) {
	int count = 0;
		for(LearnItem li : learnItemsList) {
			if (li.getStrength() == strength) {
				count++;				
			}
		}
		return count;
	}
}
