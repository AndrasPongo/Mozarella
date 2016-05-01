package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ResultContainer {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	protected Integer id;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private LearnItem learnItem;
	
	@ElementCollection
	private List<Boolean> previousAnswers;
	
	LocalDateTime timeOfLastAnswer;
	
	private Double priority = 0.0;
	
	public ResultContainer(LearnItem learnItem){
		this.learnItem = learnItem;
	}
		
	public Double getPriority(){
		//TODO discuss, then implement this
		return priority;
	}
	
	private void recalculatePriority(){
		//TODO implement this
	}
	
	public LearnItem getLearnItem(){
		return this.learnItem;
	}

	public void registerResult(Boolean isCorrect) {
		// TODO 
		
		timeOfLastAnswer = LocalDateTime.now();
		previousAnswers.add(isCorrect);
		recalculatePriority();
	}
}