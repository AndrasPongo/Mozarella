package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.joda.time.LocalDateTime;

import com.hybridtheory.mozarella.users.Student;

@Entity
public class StudentItemRecord {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	private LearnItem learnItem;
	
	@ManyToOne
	private Student student;
	
	private static final Integer numberOfStoredResults = 5;
	private Integer chosenPicture;
	private Integer strength;
	private Double priority;
	
	private Integer modificationCount=0;
	
	private LocalDateTime lastModified;
	
	public StudentItemRecord(){
		lastModified = LocalDateTime.now();
	}
	
	public StudentItemRecord(Student student, LearnItem learnItem){
		this();
		
		this.learnItem = learnItem;
		this.student = student;
	}
	
	private void registerModification(){
		if(modificationCount==null){ //for older entities that have this as null
			modificationCount=0;
		}
		
		System.out.println("registerModification has been called");
		lastModified = LocalDateTime.now();
		modificationCount += 1;
	}

	public LearnItem getLearnItem() {
		return learnItem;
	}

	public Student getStudent() {
		return student;
	}

	public static Integer getNumberofstoredresults() {
		return numberOfStoredResults;
	}

	public Integer getChosenPicture() {
		return chosenPicture;
	}

	public Integer getId() {
		return id;
	}

	public Integer getStrength() {
		return strength;
	}

	public Double getPriority() {
		return priority;
	}

	public void setPriority(Double priority) {
		this.priority = priority;
		registerModification();
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}
	
	@Override
	public String toString(){
		return this.hashCode()+ " studentItemRecord, id: "+this.id+" lastModified: "+this.lastModified;
	}
}
