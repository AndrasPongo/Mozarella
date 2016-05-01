package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import javax.persistence.Entity;

@Entity
public class Word extends LearnItem implements Comparable<LearnItem> {
	
	public Word(String text, String translation) {
		//TODO: create algorithm for ID creations
		this.setId("testWord"+Math.random()*1000);
		this.setText(text);
		this.addTranslation(translation);
		this.setStrength(0);
		this.setPriority(10);
	}


}
	
	
