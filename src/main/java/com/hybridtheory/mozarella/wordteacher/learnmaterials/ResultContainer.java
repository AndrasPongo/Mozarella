package com.hybridtheory.mozarella.wordteacher.learnmaterials;

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
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Integer id;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private LearnItem learnItem;
	
	@ElementCollection
	private List<Boolean> previousAnswers;
	
	public ResultContainer(LearnItem learnItem){
		this.learnItem = learnItem;
	}
		
	public Double getPriority(){
		return 0.0;
	}
	
	public LearnItem getLearnItem(){
		return this.learnItem;
	}
}