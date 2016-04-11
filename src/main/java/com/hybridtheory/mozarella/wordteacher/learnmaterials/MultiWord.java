package com.hybridtheory.mozarella.wordteacher.learnmaterials;

public class MultiWord extends LearnItem implements Comparable<LearnItem> {

	public MultiWord(String text, String translation) {
		//TODO: create algorithm for ID creations
		this.setId("testMultiWord"+Math.random()*1000);
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
