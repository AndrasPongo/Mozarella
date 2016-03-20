package com.hybridtheory.mozarella.wordteacher.teacher;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.Word;

public class Teacher {
	
	public boolean Evaluate(Word word, String answer) {
		return word.getTranslation().equals(answer);	
	}
}
