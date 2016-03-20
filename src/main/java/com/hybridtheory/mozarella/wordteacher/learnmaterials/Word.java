package com.hybridtheory.mozarella.wordteacher.learnmaterials;

public class Word extends AbstractLearnItem {
	
	public Word(String text) {
		this.setId("test"+Math.random()*100);
		this.setText(text);
	}
}
