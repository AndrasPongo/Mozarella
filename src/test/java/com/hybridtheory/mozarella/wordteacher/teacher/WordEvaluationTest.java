package com.hybridtheory.mozarella.wordteacher.teacher;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.*;
import com.hybridtheory.mozarella.wordteacher.teacher.Teacher;

public class WordEvaluationTest {

	@Test
	public void testEvaluations_answerIsCorrect() {
		//Given
		Teacher teacher = new Teacher();
	
		//When
		Word testword = new Word("testword", "xyz");
				
		//Then
		assertTrue(teacher.Evaluate(testword, "xyz"));
	}	
	
	@Test
	public void testEvaluations_answerIsNotCorrect() {
		//Given
		Teacher teacher = new Teacher();
		
		//When
		Word testword = new Word("testword", "xyz");
				
		//Then
		assertTrue(!teacher.Evaluate(testword, "_xyz_"));
	}	
	
}
