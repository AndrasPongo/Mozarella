package com.hybridtheory.mozarella.wordteacher.learnmaterials;

public abstract class AbstractLearnItem implements LearnItem {

	protected String idDescriptor;
	protected String text;
	protected String translation;
	protected String[] alternativeTranslation;
	protected int priority;
	protected int strength;
	protected String pictureReference;
	private String helperItem;
	
	@Override
	public void setId(String idDescriptor) {
		this.idDescriptor = idDescriptor;		
	}

	@Override
	public void setText(String text) {
		this.text = text;		
	}

	@Override
	public String getText() {
		if (this.text == null)  {
			return null;		
		}
		return this.text;
	}

	@Override
	public void setTranslation(String translation) {
		this.translation = translation;
		
	}

	@Override
	public String getTranslation() {
		if (this.translation == null)  {
			return null;		
		}
		return translation;
	}

	@Override
	public void setAlternativeTranslation(String[] alternativeTranslation) {
		this.alternativeTranslation = alternativeTranslation;		
	}

	@Override
	public String[] getAlternativeTranslations() {
		if (this.alternativeTranslation == null)  {
			return null;			
		}
		return alternativeTranslation;
	}

	@Override
	public void setStrength(int strength) {
		this.strength = strength;		
	}

	@Override
	public int getStrength() {
		if (this.strength == 0)  {
			return 0;
		}
		return strength;
	}
	
	@Override
	public void setPriority(int priority) {
		this.priority = priority;		
	}
	
	@Override
	public int getPriority() {
		if (this.priority == 0)  {
			return 0;
		}
		return priority;
	}

	@Override
	public void setPictureReference(String pictureReference) {
		this.pictureReference = pictureReference;
	}

	@Override
	public String getPictureReference() {
		if (this.pictureReference == null) {
			return null;
		}
		return this.pictureReference;
	}

	@Override
	public void setHelperItem(String helperItem) {
		this.helperItem = helperItem;		
	}

	@Override
	public String getHelperItem() {
		if (this.helperItem == null) { 
			return null;
		}
		return this.helperItem;
	}
}
