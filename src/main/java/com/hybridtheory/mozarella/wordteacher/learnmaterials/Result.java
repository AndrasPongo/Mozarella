package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Result {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	public Result(Boolean wasSuccessful){
		
	}
	
	public Result(){
		
	}
	
	private LocalDateTime timeOfAnswer;
	private Boolean wasSuccessful;
	public LocalDateTime getTimeOfAnswer() {
		return timeOfAnswer;
	}

	public Boolean getWasSuccessful() {
		return wasSuccessful;
	}
	
	
}
