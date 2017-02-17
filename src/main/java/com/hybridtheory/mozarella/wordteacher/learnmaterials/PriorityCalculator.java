package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import com.hybridtheory.mozarella.users.Student;

public interface PriorityCalculator {
	public Integer calculatePriority(Student student, LearnItem learnItem);
}
