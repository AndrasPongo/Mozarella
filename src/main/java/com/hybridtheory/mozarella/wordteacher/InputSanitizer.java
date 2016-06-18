package com.hybridtheory.mozarella.wordteacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;



public class InputSanitizer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> forbiddenWords = new ArrayList<String>();

	public InputSanitizer() {
		//TODO: initialize forbiddenWords properly here
		this.forbiddenWords.add("ForbiddenCharSequenceForTestRADNOMlkkbwesadvu84416rk2u3");
	}
	
	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}

	public boolean checkIfLearnItemInputsAreValid(String text, String translation) {
		if (text==null || translation == null) {
			return false;			
		}
		//TODO: should we allow numbers in the string at all (guess we should: why not allow "4ever" for the user if he likes it?). This also eliminates the possibility to restrict the entry of numbers on the GUI side
		if (!StringUtils.hasText(text) || 
				isNumeric(text.replace(" ", "")) || 
				!StringUtils.hasText(translation) || 
				isNumeric(translation.replace(" ", ""))) {
			return false;
		}
		return true;		
	}

	public String determineTypeOfValidLearnItemInput(String text) {
		if (!text.contains(" ")) {
			return "word";
		} else if(text instanceof String) {
			return "multi word";
		} else {
			return null;
		}
	}
	
	public boolean checkIfLearnItemsListNameIsValid(String name) {
		if (name == null || 
				!StringUtils.hasText(name) ||
				forbiddenWords.contains(name)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean checkStudentNameIsValid(String studentName) {
		//TODO: To be extended
		if (!StringUtils.hasText(studentName) || 
				studentName == null || 
				forbiddenWords.contains(studentName)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean checkPetNameIsValid(String petName) {
		//TODO: To be extended
		if (!StringUtils.hasText(petName) || 
				petName == null || 
				forbiddenWords.contains(petName)) {
			return false;
		} else {
			return true;
		}
	}
}
