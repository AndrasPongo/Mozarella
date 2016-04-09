package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.util.List;

public interface LearnItem {

	public void setId(String idDescriptor);
	
	public void setText(String text);
	public String getText();
	
	public void setTranslation(String translation);
	public String getTranslation();
	
	public void setAlternativeTranslation(String alternativeTranslation);
	public void removeAlternativeTranslation(String alternativeTranslation);
	public List<String> getAlternativeTranslations();
	
	public void setStrength(int strength);
	public int getStrength();
	
	public void setPriority(int priority);
	public int getPriority();
	
	public void setPictureReference(String pictureReference);
	public String getPictureReference();
	
	public void setHelperItem(String helperItem);
	public String getHelperItem();
}

