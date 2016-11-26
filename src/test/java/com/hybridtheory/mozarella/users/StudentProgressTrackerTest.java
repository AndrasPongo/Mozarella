package com.hybridtheory.mozarella.users;

/*we don't use studentprogresstracker because of the inefficiency
public class StudentProgressTrackerTest {
	
	@Test
	public void testInit_correctInputs() {
		//Given
		Student student = new Student();
		student.setName("testStudent");
			
		//When
		StudentProgressTracker studentProgressTracker = new StudentProgressTracker(student);

				
		//Then
		assertTrue(studentProgressTracker.getOwner() == student);
	}	
	
	@Test(expected = IllegalArgumentException.class)
	public void testInit_incorrectInputs() {
		//Given
			
		//When & Then
		new StudentProgressTracker(null);
	}	
	
	@Test
	public void testLearntCount_correctInputs_noLearntWords() {
		//Given
		Student student = new Student();
		LearnItemList learnItemsList = student.addNewLearnItemsList("testLearnItemsList");

		LearnItem learnItem = new LearnItem("testword", "xyz");
		student.addNewLearnItemToExistingList(learnItemsList, learnItem);
		
		StudentProgressTracker studentProgressTracker = new StudentProgressTracker(student);

		//When
		int learntItemsCount = studentProgressTracker.returnLearntWordsCountOfStudent();
		System.out.println(learntItemsCount);
				
		//Then
		assertTrue(learntItemsCount==0);
	}

	@Test
	public void testLearntCount_correctInputs_withLearntWord() {
		//Given
		Student student = new Student();
		LearnItemList learnItemsList = student.addNewLearnItemsList("testLearnItemsList");
		LearnItem learnItem = new LearnItem("testword", "xyz");
		student.addNewLearnItemToExistingList(learnItemsList, learnItem);
		student.getLearnItemListByName("testLearnItemsList").getLearnItem("testword").setStrength(1);
		
		StudentProgressTracker studentProgressTracker = new StudentProgressTracker(student);

		//When
		int learntItemsCount = studentProgressTracker.returnLearntWordsCountOfStudent();

		//Then
		assertTrue(learntItemsCount==1);
	}
} */
