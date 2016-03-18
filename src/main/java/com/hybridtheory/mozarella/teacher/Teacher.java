package com.hybridtheory.mozarella.teacher;

import com.hybridtheory.mozarella.learnmaterials.Word;

public class Teacher {
	
	public boolean Evaluate(Word word, String answer) {
		if (word.getTranslation() == answer) {
			return true;
		} else {
			return false;
		}
	}	
}
