package com.hybridtheory.mozarella.wordteacher.priority;

import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;

public interface PriorityCalculator {
	public Double calculatePriority(Student student, LearnItem learnItem);
}
