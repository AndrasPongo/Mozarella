package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.hybridtheory.mozarella.users.Student;

@Entity
public class LearnItemManager implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@OneToOne(mappedBy = "learnItemManager")
	private Student owner;

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<LearnItemList> learnItemLists = new ArrayList<LearnItemList>();
	
	private static final long serialVersionUID = 7526471291622776147L;

	public LearnItemManager() {

	}

	// TODO: Is this good design? Like this every user will have their own
	// LearnItemManager, which will raise the need towards the User object to
	// know about this Manager itself. Not sure if this is necessary...
	public LearnItemManager(Student student) {
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
		if (name != null && !learnItemLists.contains(newLearnItemsList)) {
			learnItemLists.add(newLearnItemsList);
			return newLearnItemsList;
		} else {
			throw new IllegalArgumentException("Invalid input when creating LearnItemsList");
		}
	}

	public List<LearnItemList> getLearnItemsLists() {
		// TODO
		return learnItemLists;
	}

	// public LearnItemsList getLearnItemsList(LearnItemsList learnItemsList) {
	public LearnItemList getLearnItemsList(String nameOfList) {
		for (LearnItemList lit : learnItemLists) {
			if (lit.getName().equals(nameOfList)) {
				return lit;
			}
		}
		return null;
	}

	public void createNewLearnItemToLearnItemsList(LearnItemList learnItemsList, String text, String translation) {
		if (learnItemsList == null || !learnItemLists.contains(learnItemsList)) {
			throw new IllegalArgumentException("Invalid input when creating LearnItemsList");
		} else {
			LearnItem newLearnItem = null;
			newLearnItem = new LearnItem(text, translation);
			learnItemsList.addLearnItem(newLearnItem);
		}
	}

	public void addNewLearnItemToLearnItemsList(LearnItemList learnItemsList, LearnItem learnItem) {
		if (learnItemsList == null || !learnItemLists.contains(learnItemsList) || learnItem == null) {
			throw new IllegalArgumentException("Invalid input when creating LearnItemsList");
		} else {
			learnItemsList.addLearnItem(learnItem);
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

		return learnItemLists.stream().map(LearnItemList::getName).collect(Collectors.toList());

	}

	public LearnItemList addNewLearnItemsList(LearnItemList learnItemsList) {
		if (!learnItemLists.contains(learnItemsList)) {
			learnItemLists.add(learnItemsList);
		}
		return learnItemsList;
	}

}