package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.hybridtheory.mozarella.users.Student;

public class LearnItemsManager {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private Student owner;
	private List<LearnItemsList> learnItemsLists = new ArrayList<LearnItemsList>();
	private LearnItemFactory learnItemFactory = new LearnItemFactory();
	private List<ResultContainer> results = new ArrayList<ResultContainer>();
	
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
		if (name != null && !learnItemsLists.contains(newLearnItemsList)) {
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
	
//	public LearnItemsList getLearnItemsList(LearnItemsList learnItemsList) {
	public LearnItemsList getLearnItemsList(String nameOfList) {
		for (LearnItemsList lit : learnItemsLists) {
			if (lit.getName() == nameOfList) {
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
	
	public void addNewLearnItemToLearnItemsList(LearnItemsList learnItemsList, LearnItem learnItem) {
		if (learnItemsList == null || !learnItemsLists.contains(learnItemsList) || learnItem == null) {
			throw new IllegalArgumentException("Invalid input when creating LearnItemsList");
		} else {
			learnItemsList.addLearnItem(learnItem);
		}
	}	
	
	public LearnItem getLearnItemFromList(LearnItemsList learnItemsList, String textOfLearnItem) {
		//TODO: checks
		return learnItemsList.getLearnItem(textOfLearnItem);
	}

	//TODO: add alternative(s) to existing words in list
	
	public void modifyExitingLearnItem_newText(LearnItem learnItem, String newText) {
		if (learnItem != null) {
			learnItem.setText(newText);
		} else {
			throw new IllegalArgumentException("Invalid input when creating setting new Text for Learn Item");
		}
	}
	
	public void modifyExitingLearnItem_newTranslation(LearnItem learnItem, String newTranslation) {
		if (learnItem != null) {
			learnItem.setText(newTranslation);
		} else {
			throw new IllegalArgumentException("Invalid input when creating setting new Translation for Learn Item");
		}
	}
	
	public void modifyExitingLearnItem_newAlternativeTranslation(LearnItem learnItem, String newAlternativeTranslation) {
		if (learnItem != null) {
			learnItem.setAlternativeTranslation(newAlternativeTranslation);
		} else {
			throw new IllegalArgumentException("Invalid input when creating setting new Translation for Learn Item");
		}
	}
	
	public void modifyExitingLearnItem_removeAlternativeTranslation(LearnItem learnItem, String newAlternativeTranslation) {
		if (learnItem != null) {
			learnItem.setAlternativeTranslation(newAlternativeTranslation);
		} else {
			throw new IllegalArgumentException("Invalid input when creating setting new Translation for Learn Item");
		}
	}
	
	public void setPriorityOfLearnItem(LearnItem learnItem, int priority) {
		if (learnItem != null && priority >= 1 && priority <= 10) {
			learnItem.setPriority(priority);
		} else {
			throw new IllegalArgumentException("Invalid input when creating setting strength for Learn Item");
		}
	}
	
	public void setStrengthOfLearnItem(LearnItem learnItem, int strength) {
		if (learnItem != null && strength >= 0 && strength <= 10) {
			learnItem.setStrength(strength);
		} else {
			throw new IllegalArgumentException("Invalid input when creating setting strength for Learn Item");
		}
	}
	
	
	public void removeLearnItemFromLearnItemsList(LearnItemsList learnItemsList, LearnItem learnItem) {
		//TODO: checks
		learnItemsList.removeLearnItem(getLearnItemFromList(learnItemsList, learnItem.getText()));
	}
	
	protected List<String> getExistingListNames() {

		return learnItemsLists.stream().map(LearnItemsList::getName).collect(Collectors.toList());
		
	}

	public LearnItemsList addNewLearnItemsList(LearnItemsList learnItemsList) {
		if(!learnItemsLists.contains(learnItemsList)){
			learnItemsLists.add(learnItemsList);
		}
		return learnItemsList;
	}
	
	public List<LearnItem> getLearnItemsToPractice(List<Integer> learnItemListIds, Integer numberOfLearnItems){
		
		Stream<LearnItem> alreadyPracticedLearnItems =
		results.stream().filter(result->result.getPriority()>5)
		.map(ResultContainer::getLearnItem)
		.filter(learnItem -> learnItemListIds.contains(learnItem.getLearnItemsList().getId()))
		.limit(numberOfLearnItems);
		
		Stream<LearnItem> newLearnItemsForUser = learnItemsLists
		.stream()
		.filter(learnItemList -> learnItemListIds.contains(learnItemList.getId()))
		.map(learnItemList -> learnItemList.getLearnItems())
		.flatMap(learnItems -> learnItems.stream())
		.limit(numberOfLearnItems);
		
		Stream <LearnItem> learnItemsToReturn = Stream.concat(alreadyPracticedLearnItems, newLearnItemsForUser)
		.limit(numberOfLearnItems);
		
		return learnItemsToReturn.collect(Collectors.toList());
	}
}