package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.InputSanitizer;

@Entity
public class LearnItemList /*implements Iterable<LearnItem>*/ {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToMany
	private List<Student> contributors = new ArrayList<>();
	
	@ManyToMany(mappedBy="learnItemLists")
	private Set<Student> students = new HashSet<>();
	
	private Boolean isPublic;
	
	private Boolean isActivated = false;

	private String fromLanguageCode;

	private String toLanguageCode;

	private String name = "";

	private String description;
	
	@OneToMany(mappedBy="learnItemList", cascade = { CascadeType.ALL }, fetch=FetchType.LAZY)
	@OrderBy("priority")
	@JsonIgnore
	private SortedSet<LearnItem> learnItems = new TreeSet<LearnItem>();
	private int numberOfLearnItems = 0;
	
	@Transient
	private InputSanitizer inputSanitizer = new InputSanitizer();
	private boolean validName = false;
	
	@ManyToOne
	private Student owner;

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
	
	public int getNumberOfLearnItems() {
		return this.numberOfLearnItems;
	}

//	@Override
//	public Iterator<LearnItem> iterator() {
//		return learnItems.iterator();
//	}

	public Integer getId() {
		return id;
	}

	@JsonIgnore
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
	
	@Override
	public boolean equals(Object other) {
		return other instanceof LearnItemList && this.id.equals(((LearnItemList)(other)).id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	//TODO unit test
	public void addLearnItem(LearnItem learnItem) {
		if(learnItems.add(learnItem)){
			numberOfLearnItems+=1;
			learnItem.setLearnItemsList(this);
		}
	}
	
	public void addStudent(Student student){
		this.students.add(student);
	}
	
	public void removeStudent(Student student){
		this.students.remove(student);
	}

	public String getFromLanguageCode() {
		return fromLanguageCode;
	}

	public void setFromLanguageCode(String fromLanguageCode) {
		this.fromLanguageCode = fromLanguageCode;
	}

	public String getToLanguageCode() {
		return toLanguageCode;
	}

	public void setToLanguageCode(String toLanguageCode) {
		this.toLanguageCode = toLanguageCode;
	}

	public Boolean getIsActivated() {
		return isActivated;
	}

	public void setIsActivated(Boolean isActivated) {
		this.isActivated = isActivated;
	}

	//TODO unit test
	public boolean removeLearnItem(LearnItem learnItem) {
		Boolean wasSuccessful = learnItems.remove(learnItem);
		if(wasSuccessful){
			numberOfLearnItems-=1;
			learnItem.setLearnItemsList(null);
		}
		return wasSuccessful;
	}

	public Student getOwner() {
		return owner;
	}

	public void setOwner(Student owner) {
		this.owner = owner;
	}
	
}