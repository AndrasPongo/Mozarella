package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.teacher.ItemPrioritizer;

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
	
	@Transient
	@Autowired
	private ItemPrioritizer itemPrioritizer;
	
	private static final Integer numberOfStoredResults = 5;
	private Integer chosenPicture;
	private Integer strength;
	private Double priority;
	
	public StudentItemRecord(){
		
	}
	
	public StudentItemRecord(Student student, LearnItem learnItem){
		this.learnItem = learnItem;
		this.student = student;
	}
	
	public void registerResult(Boolean wasSuccessful){
		Result r = new Result(wasSuccessful, this);
		results.add(r);
		recalculatePriority();
	}
	
	private void recalculatePriority(){
		//this.priority = itemPrioritizer.assignPriority(results);
		//can't inject itemPrioritizer, probably because this entity is not in the appcontext
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

	public Double getPriority() {
		return priority;
	}
}
