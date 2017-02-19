package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	
	private LocalDateTime lastModified;
	
	public StudentItemRecord(){
		
	}
	
	public StudentItemRecord(Student student, LearnItem learnItem){
		this.learnItem = learnItem;
		this.student = student;
		
		lastModified = LocalDateTime.now();
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
		lastModified = LocalDateTime.now();
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}
	
}
