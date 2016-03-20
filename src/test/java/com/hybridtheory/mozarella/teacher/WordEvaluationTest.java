package com.hybridtheory.mozarella.teacher;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.*;
import com.hybridtheory.mozarella.wordteacher.teacher.Teacher;

public class WordEvaluationTest {

	@Test
	public void testEvaluations_answerIsCorrect() {
		//Given
		Teacher teacher = new Teacher();
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		Word word = (Word)learnItemFactory.createLearnItem("testword");
		
		//When
		word.setTranslation("xyz");
		
		//Then
		assertTrue(teacher.Evaluate(word, "xyz") == true);
	}	
	
	@Test
	public void testEvaluations_answerIsNotCorrect() {
		//Given
		Teacher teacher = new Teacher();
		LearnItemFactory learnItemFactory = new LearnItemFactory();
		Word word = (Word)learnItemFactory.createLearnItem("testword");
		
		//When
		word.setTranslation("xyz");
		
		//Then
		assertTrue(teacher.Evaluate(word, "_xyz_") == false);
	}	
	
}
