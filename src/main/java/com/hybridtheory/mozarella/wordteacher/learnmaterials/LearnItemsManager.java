package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.util.*;
import com.hybridtheory.mozarella.users.Student;

public class LearnItemsManager {
	private Student owner = null;
	private List<LearnItemsList> learnItemsLists = new ArrayList<LearnItemsList>();
	private LearnItemFactory learnItemFactory = new LearnItemFactory();

	//TODO: Is this good design? Like this every user will have their own LearnItemManager, which will raise the need towards the User object to know about this Manager itself. Not sure if this is necessary...
	public LearnItemsManager(Student student) {
		if (student != null) {
			this.owner = student;
		} else {
			throw new IllegalArgumentException("Invalid input when creating LearnItemsList");
		}
	}

	public Student getOwner() {
		return owner;
	}

	public LearnItemsList createLearnItemsList(String name) {
		LearnItemsList newLearnItemsList = new LearnItemsList(name);
		if (name != null || !learnItemsLists.contains(newLearnItemsList)) {
			learnItemsLists.add(newLearnItemsList);
			return newLearnItemsList;
		} else {
			throw new IllegalArgumentException("Invalid input when creating LearnItemsList");
		}
	}
	
	public List<LearnItemsList> getLearnItemsLists() {
		//TODO
		return learnItemsLists;
	}
	
	public LearnItemsList getLearnItemsList(LearnItemsList learnItemsList) {
		for (LearnItemsList lit : learnItemsLists) {
			if (lit == learnItemsList) {
				return lit;
			}
		}
		return null;
	}
	
	public void createNewLearnItemToLearnItemsList(LearnItemsList learnItemsList, String text, String translation) {
		if (learnItemsList == null || !learnItemsLists.contains(learnItemsList)) {
			throw new IllegalArgumentException("Invalid input when creating LearnItemsList");
		} else {
			LearnItem newLearnItem = null;
			newLearnItem = learnItemFactory.createLearnItem(text, translation);
			learnItemsList.addLearnItem(newLearnItem);
		}
	}
	
	public LearnItem getLearnItemFromList(LearnItemsList learnItemsList, String textOfLearnItem) {
		//TODO: checks
		return learnItemsList.getLearnItem(textOfLearnItem);
	}

	//TODO: modify existing word in list
	//TODO: add alternative(s) to existing words in list
	//TODO: modify priority of learn item
	//TODO: modify strength
	
	public void removeLearnItemFromLearnItemsList(LearnItemsList learnItemsList, LearnItem learnItem) {
		//TODO: checks
		learnItemsList.removeLearnItem(getLearnItemFromList(learnItemsList, learnItem.getText()));
	}
}