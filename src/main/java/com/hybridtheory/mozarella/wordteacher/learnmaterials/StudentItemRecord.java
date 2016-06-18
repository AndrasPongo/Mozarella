package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.hybridtheory.mozarella.users.Student;

@Entity
public class StudentItemRecord {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	private LearnItem learnItem;
	
	@ManyToOne
	private Student student;
	
	@OneToMany(mappedBy="record")
	private List<Result> results = new LinkedList<Result>();
	
	private static final Integer numberOfStoredResults = 5;
	private Integer chosenPicture;
	private Integer strength;
	private Integer priority;
	
	public StudentItemRecord(){
		
	}
	
	public StudentItemRecord(Student student, LearnItem learnItem){
		this.learnItem = learnItem;
		this.student = student;
	}
	
	public void registerResult(Boolean wasSuccessful){
		Result r = new Result(wasSuccessful, this);
		results.add(r);
	}
	
	private void recaluclatePriority(){
		//TODO: use a separate class for this purpose 
	}

	public LearnItem getLearnItem() {
		return learnItem;
	}

	public Student getStudent() {
		return student;
	}

	public List<Result> getResults() {
		return results;
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

	public Integer getPriority() {
		return priority;
	}
}
