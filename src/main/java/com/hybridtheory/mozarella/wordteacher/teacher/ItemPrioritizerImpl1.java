package com.hybridtheory.mozarella.wordteacher.teacher;

import java.util.List;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;

public class ItemPrioritizerImpl1 implements ItemPrioritizer {

	@Override
	public Double assignPriority(List<Result> results) {
		Integer sum = results.stream().map(result->{
			if(result.getWasSuccessful()){
				return 1;
			} else {
				return 0;
			}
		})
		.reduce((sum1,sum2)->sum1+sum2).get();
		
		return (double)sum/(double)results.size();
	}
}
