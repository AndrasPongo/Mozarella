package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.hybridtheory.mozarella.users.Student;

@Entity
public class ResultsContainer {
	
	private ArrayList<Result> results = new ArrayList<Result>();
	private Integer priority = 0;
	
	public ResultsContainer(){
		
	}
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	protected Integer id;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private LearnItem learnItem;
	
	@ManyToOne()
	private Student owner;
	
	public void registerResult(Boolean wasSuccessful){
		Result r = new Result(wasSuccessful);
		results.add(r);
	}
	
	private void recaluclatePriority(){
		//TODO: use a separate class for this purpose 
	}
	
}