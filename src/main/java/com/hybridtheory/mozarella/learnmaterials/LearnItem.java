package com.hybridtheory.mozarella.learnmaterials;

public interface LearnItem {

	public void setId(String idDescriptor);
	
	public void setText(String text);
	public String getText();
	
	public void setTranslation(String translation);
	public String getTranslation();
	
	public void setAlternativeTranslation(String[] alternativeTranslation);
	public String[] getAlternativeTranslations();
	
	public void setStrength(int strength);
	public int getStrength();
	
	public void setPictureReference(String pictureReference);
	public String getPictureReference();
	
	public void setHelperItem(String helperItem);
	public String getHelperItem();	
}

