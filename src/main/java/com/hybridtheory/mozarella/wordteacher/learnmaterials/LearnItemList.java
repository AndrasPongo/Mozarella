package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.InputSanitizer;

@Entity
public class LearnItemList implements Comparable<LearnItemList>, Iterable<LearnItem> {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToMany
	private List<Student> contributors = new ArrayList<Student>();
	
	private Boolean isPublic;

	private String fromLanguage;

	private String toLanguage;

	private String name = "";

	private String description;
	
	@OneToMany(mappedBy="learnItemList", cascade = { CascadeType.ALL }, fetch=FetchType.LAZY)
	@OrderBy("priority")
	@JsonIgnore
	private SortedSet<LearnItem> learnItems = new TreeSet<LearnItem>();
	private int numberOfLearnItemsInList = 0;
	
	@Transient
	private InputSanitizer inputSanitizer = new InputSanitizer();
	private boolean validName = false;

	public LearnItemList(){
		
	}
	
	public LearnItemList(String name) {
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
	
	public String getName() {
		return this.name;
	}
	
	public int getNumberOfLearnItemsInList() {
		return this.numberOfLearnItemsInList;
	}
	
	public Boolean addLearnItem(LearnItem learnItem) {
		if (learnItem == null || this.learnItems.contains(learnItem)) {
			return false;
		} else {
			learnItem.setLearnItemsList(this);
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
	public int compareTo(LearnItemList learnItemsList) {
		if (learnItemsList.getName() == this.name) {
			return 0;	
		} else {
			return -1;
		}
	}	
	
	@Override
	public boolean equals(Object other) {
		return other instanceof LearnItemList && this.name.equals(((LearnItemList)(other)).name);
	}

	@Override
	public Iterator<LearnItem> iterator() {
		return learnItems.iterator();
	}

	public Integer getId() {
		return id;
	}
	
	@JsonIgnore
	public Collection<LearnItem> getLearnItems(){
		return learnItems;
	}

	public List<Student> getContributors() {
		return contributors;
	}

	public void setContributors(List<Student> contributors) {
		this.contributors = contributors;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}