package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.hybridtheory.mozarella.users.Student;

@Entity
public class Result{
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Boolean wasSuccessful;
	
	@ManyToOne
	private StudentItemRecord record;
	
	public Result(Boolean wasSuccessful, Student student, LearnItem learnItem){
		this.wasSuccessful = wasSuccessful;
		this.record = record;
	}
	
	public Result(){
		
	}
	
	private LocalDateTime timeOfAnswer;

	public LocalDateTime getTimeOfAnswer() {
		return timeOfAnswer;
	}

	public Boolean getWasSuccessful() {
		return wasSuccessful;
	}
	
}
