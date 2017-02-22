package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.hybridtheory.mozarella.users.Student;

@Entity
public class Result{
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Boolean wasSuccessful;
	
	@ManyToOne
	private Student student;
	
	@ManyToOne
	private LearnItem learnItem;
	
	private LocalDateTime time;
	
	public Result(Boolean wasSuccessful, Student student, LearnItem learnItem){
		this.wasSuccessful = wasSuccessful;
		this.student = student;
		this.learnItem = learnItem;
		this.time = LocalDateTime.now();
	}
	
	public Result(){
		this.time = LocalDateTime.now();
	}
	
	private LocalDateTime timeOfAnswer;

	public LocalDateTime getTimeOfAnswer() {
		return timeOfAnswer;
	}

	public Boolean getWasSuccessful() {
		return wasSuccessful;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public LearnItem getLearnItem() {
		return learnItem;
	}
	
	public Integer getId(){
		return id;
	}
	
}
