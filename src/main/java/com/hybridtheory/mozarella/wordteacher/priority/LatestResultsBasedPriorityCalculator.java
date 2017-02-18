package com.hybridtheory.mozarella.wordteacher.priority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.hybridtheory.mozarella.persistence.repository.ResultRepository;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;

//TODO: test this
//possible performance improvement: don't rely on the database, pass old result to the frontend and back
public class LatestResultsBasedPriorityCalculator implements PriorityCalculator {
	
	@Autowired
	private ResultRepository resultRepository;
	private Integer numberOfResultsToConsider;
	
	public LatestResultsBasedPriorityCalculator(Integer numberOfResultsToConsider){
		this.numberOfResultsToConsider = numberOfResultsToConsider;
	}

	@Override
	public Double calculatePriority(Student student, LearnItem learnItem) {
		List<Result> lastResults = resultRepository.getLastResultsForStudentAndLearnItem(student.getId(), learnItem.getId(), new PageRequest(0,numberOfResultsToConsider));
		
		Double prio = 0.0;
		Integer i=0;
		
		for(Result result : lastResults){
			if(!result.getWasSuccessful()){
				prio += ((Double.valueOf(numberOfResultsToConsider-i))/Double.valueOf(numberOfResultsToConsider));
			}
			
			i+=1;
		}
		
		return prio;
	}
	
}
