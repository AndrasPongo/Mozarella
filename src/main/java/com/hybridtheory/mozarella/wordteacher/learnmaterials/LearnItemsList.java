package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.InputSanitizer;

@Entity
public class LearnItemsList implements Comparable<LearnItemsList>, Iterable<LearnItem> {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name = "";
	
	@OneToMany(mappedBy="learnItemsList", fetch=FetchType.LAZY)
	@OrderBy("priority")
	private SortedSet<LearnItem> learnItems = new TreeSet<LearnItem>();
	private int numberOfLearnItemsInList = 0;
	
	@Transient
	private InputSanitizer inputSanitizer = new InputSanitizer();
	private boolean validName = false;

	public LearnItemsList(String name) {
		validName = inputSanitizer.checkIfLearnItemsListNameIsValid(name);
		if (!validName) {
			throw new IllegalArgumentException("Invalid input when creating LearnItemsList");
		} else {
			//TODO: create algorithm for ID creations
			this.name = name;
		}
	}
	
	protected void setName(String name) {
		validName = inputSanitizer.checkIfLearnItemsListNameIsValid(name);
		if (!validName) {
			throw new IllegalArgumentException("Invalid input when creating LearnItemsList");
		} else {
			this.name = name;
		}
	}
	
	public Integer getID() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getNumberOfLearnItemsInList() {
		return this.numberOfLearnItemsInList;
	}
	
	protected boolean addLearnItem(LearnItem learnItem) {
		if (learnItem == null || this.learnItems.contains(learnItem)) {
			return false;
		} else {
			learnItems.add(learnItem);
			numberOfLearnItemsInList++;
			return true;
		}
	}
	
	
	public LearnItem getLearnItem(String text) {
		//TODO: at this point it became apparent that we need to separate the check for the text and the translation. Will do. For now... CHEAT
		if (!inputSanitizer.checkIfLearnItemInputsAreValid(text, "translationThatCircumventsTheSanitizer")) {
			return null;
		} else {
			for (LearnItem li : learnItems) {
				if (li.getText()==text) {
					return li;
				}
			}
		}
		return null;
	}
	
	protected boolean removeLearnItem(LearnItem learnItem) {
		if (learnItem == null || !this.learnItems.contains(learnItem)) {
			return false;
		} else {
			learnItems.remove(learnItem);
			numberOfLearnItemsInList--;
			return true;
		}
	}

	@Override
	public int compareTo(LearnItemsList learnItemsList) {
		if (learnItemsList.getName() == this.name) {
			return 0;	
		} else {
			return -1;
		}
	}	
	
	@Override
	public boolean equals(Object other) {
		return other instanceof LearnItemsList && this.name.equals(((LearnItemsList)(other)).name);
	}

	@Override
	public Iterator<LearnItem> iterator() {
		return learnItems.iterator();
	}

	public Integer getId() {
		return id;
	}
	
	public Collection<LearnItem> getLearnItems(){
		return learnItems;
	}
	
}