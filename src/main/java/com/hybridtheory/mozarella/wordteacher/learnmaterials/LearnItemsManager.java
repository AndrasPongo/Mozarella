package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.hybridtheory.mozarella.users.Student;

@Entity
public class LearnItemsManager {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@OneToOne(mappedBy = "learnItemManager")
	private Student owner;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private List<LearnItemList> learnItemsLists = new ArrayList<LearnItemList>();

	@Transient
	private LearnItemFactory learnItemFactory = new LearnItemFactory();

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@OrderBy("priority")
	private List<ResultContainer> results = new ArrayList<ResultContainer>();

	public LearnItemsManager() {

	}

	// TODO: Is this good design? Like this every user will have their own
	// LearnItemManager, which will raise the need towards the User object to
	// know about this Manager itself. Not sure if this is necessary...
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

	public LearnItemList createLearnItemsList(String name) {
		LearnItemList newLearnItemsList = new LearnItemList(name);
		if (name != null && !learnItemsLists.contains(newLearnItemsList)) {
			learnItemsLists.add(newLearnItemsList);
			return newLearnItemsList;
		} else {
			throw new IllegalArgumentException("Invalid input when creating LearnItemsList");
		}
	}

	public List<LearnItemList> getLearnItemsLists() {
		// TODO
		return learnItemsLists;
	}

	// public LearnItemsList getLearnItemsList(LearnItemsList learnItemsList) {
	public LearnItemList getLearnItemsList(String nameOfList) {
		for (LearnItemList lit : learnItemsLists) {
			if (lit.getName() == nameOfList) {
				return lit;
			}
		}
		return null;
	}

	public void createNewLearnItemToLearnItemsList(LearnItemList learnItemsList, String text, String translation) {
		if (learnItemsList == null || !learnItemsLists.contains(learnItemsList)) {
			throw new IllegalArgumentException("Invalid input when creating LearnItemsList");
		} else {
			LearnItem newLearnItem = null;
			newLearnItem = learnItemFactory.createLearnItem(text, translation);
			learnItemsList.addLearnItem(newLearnItem);
		}
	}

	public void addNewLearnItemToLearnItemsList(LearnItemList learnItemsList, LearnItem learnItem) {
		if (learnItemsList == null || !learnItemsLists.contains(learnItemsList) || learnItem == null) {
			throw new IllegalArgumentException("Invalid input when creating LearnItemsList");
		} else {
			learnItemsList.addLearnItem(learnItem);
			results.add(new ResultContainer(learnItem));
		}
	}

	public LearnItem getLearnItemFromList(LearnItemList learnItemsList, String textOfLearnItem) {
		// TODO: checks
		return learnItemsList.getLearnItem(textOfLearnItem);
	}

	// TODO: add alternative(s) to existing words in list

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

	public void modifyExitingLearnItem_newAlternativeTranslation(LearnItem learnItem,
			String newAlternativeTranslation) {
		if (learnItem != null) {
			learnItem.addTranslation(newAlternativeTranslation);
		} else {
			throw new IllegalArgumentException("Invalid input when creating setting new Translation for Learn Item");
		}
	}

	public void modifyExitingLearnItem_removeAlternativeTranslation(LearnItem learnItem,
			String newAlternativeTranslation) {
		if (learnItem != null) {
			learnItem.addTranslation(newAlternativeTranslation);
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

	public void removeLearnItemFromLearnItemsList(LearnItemList learnItemsList, LearnItem learnItem) {
		// TODO: checks
		learnItemsList.removeLearnItem(getLearnItemFromList(learnItemsList, learnItem.getText()));
	}

	protected List<String> getExistingListNames() {

		return learnItemsLists.stream().map(LearnItemList::getName).collect(Collectors.toList());

	}

	public LearnItemList addNewLearnItemsList(LearnItemList learnItemsList) {
		if (!learnItemsLists.contains(learnItemsList)) {
			learnItemsLists.add(learnItemsList);
		}
		return learnItemsList;
	}

	private ResultContainer createResult(LearnItem learnItem){
			ResultContainer newResultContainer = new ResultContainer(learnItem);
			results.add(newResultContainer);
			return newResultContainer;
	}
	
	public void acceptResult(ResultContainer toUpdate, Boolean isCorrect){
		toUpdate.registerResult(isCorrect);
		if(!results.contains(toUpdate)){
			results.add(toUpdate);
		}
	}
	
	public List<LearnItem> getLearnItemsToPractice(List<Integer> learnItemListIds, Integer numberOfLearnItems) {

		Stream<LearnItem> alreadyPracticedLearnItems = results.stream().filter(result -> result.getPriority() > 5)
				.map(ResultContainer::getLearnItem)
				.filter(learnItem -> learnItemListIds.contains(learnItem.getLearnItemsList().getId()))
				.limit(numberOfLearnItems);

		Stream<LearnItem> newLearnItemsForUser = learnItemsLists.stream()
				.filter(learnItemList -> learnItemListIds.contains(learnItemList.getId()))
				.map(learnItemList -> learnItemList.getLearnItems()).flatMap(learnItems -> learnItems.stream())
				.limit(numberOfLearnItems);

		Stream<LearnItem> learnItemsToReturn = Stream.concat(alreadyPracticedLearnItems, newLearnItemsForUser)
				.limit(numberOfLearnItems);

		return learnItemsToReturn.collect(Collectors.toList());
	}
}