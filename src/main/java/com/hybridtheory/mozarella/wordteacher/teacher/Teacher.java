package com.hybridtheory.mozarella.wordteacher.teacher;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.Word;

public class Teacher { //TODO: delete?
	
	public boolean Evaluate(Word word, String answer) {
		return word.getTranslation().equals(answer);	
	}
	
	
}
