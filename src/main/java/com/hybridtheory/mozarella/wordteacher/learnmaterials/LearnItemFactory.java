package com.hybridtheory.mozarella.wordteacher.learnmaterials;

public class LearnItemFactory {
	
	public LearnItem createLearnItem(String text) {
		if (text==null) {
			throw new IllegalArgumentException();
		}
		
		//TODO: should we allow numbers in the string at all (guess we should: why not allow "4ever" for the user if he likes it?). This also eliminates the possibility to restrict the entry of numbers on the GUI side
		text.trim();
		if (text == "" || isNumeric(text)) {
			throw new IllegalArgumentException("Invalid input");
		}

		if (!text.contains(" ")) {
			return new Word(text);
		} else if(text instanceof String) {
			return new MultiWord();
		} else {
			throw new IllegalArgumentException("Invalid input");
		}
	}
	
	private static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}	
}
