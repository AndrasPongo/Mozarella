package com.hybridtheory.mozarella.users;

import java.util.ArrayList;
import java.util.List;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;

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
	
	private List<String> returnLearntWordsOfStudent() {
		List<String> alreadyLearntWords = new ArrayList<String>();
		List<LearnItemList> learnItemListsOfStudent = this.owner.getLearnItemLists();
		for(LearnItemList learnItemsList : learnItemListsOfStudent) {
			for(LearnItem learnItem : learnItemsList) {
				if (learnItem.getStrength() > 0) {
					alreadyLearntWords.add(learnItem.getText());
				}
			}
	    }
		return alreadyLearntWords;		
	}
	
	public int returnLearntWordsCountOfStudent() {
		List<String> alreadyLearntWords = returnLearntWordsOfStudent();
		if (alreadyLearntWords == null) {
			return 0;
		}
		return alreadyLearntWords.size();
	}
	
	
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
