package com.hybridtheory.mozarella.wordteacher.learnmaterials;

public class Word extends AbstractLearnItem implements Comparable<LearnItem> {
	
	public Word(String text, String translation) {
		//TODO: create algorithm for ID creations
		this.setId("testWord"+Math.random()*1000);
		this.setText(text);
		this.setTranslation(translation);
		this.setStrength(1);
		this.setPriority(10);
	}

	public int compareTo(LearnItem learnItem) {
		if (learnItem.getText() == this.text) { 
			return 0;	
		} else {
			return -1;
		}
	}
}
	
	
