package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.util.List;

public class ResultContainer {
	
	private LearnItem learnItem;
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