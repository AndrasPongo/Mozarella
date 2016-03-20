package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import com.hybridtheory.mozarella.wordteacher.InputSanitizer;

public class LearnItemFactory {
	private String typeOfInput = "";
	private boolean validInput = false;
	private InputSanitizer inputSanitizer = new InputSanitizer();
	
	public LearnItem createLearnItem(String text, String translation) {
		validInput = inputSanitizer.checkIfLearnItemInputsAreValid(text, translation);
		if (!validInput) {
			throw new IllegalArgumentException("Invalid input when creating LearnItem");
		}
		
		typeOfInput = inputSanitizer.determineTypeOfValidLearnItemInput(text);
		if (typeOfInput == "word") {
			return new Word(text, translation);
		} else if (typeOfInput == "multi word"){
			return new MultiWord(text, translation);
		} else {
			throw new IllegalArgumentException("Invalid input when creating LearnItem");
		}
	}
}
