package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class LearnItem {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch=FetchType.LAZY)
	protected LearnItemsList learnItemsList;
	protected String idDescriptor;
	protected String text;
	protected String translation;
	
	@ElementCollection
	protected List<String> alternativeTranslation  = new ArrayList<String>();
	protected double priority;
	protected int strength;
	protected String pictureReference;
	private String helperItem;
	
	public void setId(String idDescriptor) {
		this.idDescriptor = idDescriptor;		
	}

	public void setText(String text) {
		this.text = text;		
	}

	public String getText() {
		if (this.text == null)  {
			return null;		
		}
		return this.text;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
		
	}

	public String getTranslation() {
		if (this.translation == null)  {
			return null;		
		}
		return translation;
	}

	public void setAlternativeTranslation(String alternativeTranslation) {
		this.alternativeTranslation.add(alternativeTranslation);		
	}

	public void removeAlternativeTranslation(String alternativeTranslation) {
		if (alternativeTranslation == null || !this.alternativeTranslation.contains(alternativeTranslation)) {
			throw new IllegalArgumentException("Alternative translation doesn't exists for this Learn Item");
		} else {
			this.alternativeTranslation.remove(this.alternativeTranslation.indexOf(alternativeTranslation));

		}
	}
	
	public List<String> getAlternativeTranslations() {
		if (this.alternativeTranslation == null)  {
			return new ArrayList<String>();			
		}
		return alternativeTranslation;
	}

	public void setStrength(int strength) {
		this.strength = strength;		
	}

	public int getStrength() {
		if (this.strength == 0)  {
			return 0;
		}
		return strength;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;		
	}
	
	public double getPriority() {
		return priority;
	}

	public void setPictureReference(String pictureReference) {
		this.pictureReference = pictureReference;
	}

	public String getPictureReference() {
		if (this.pictureReference == null) {
			return null;
		}
		return this.pictureReference;
	}

	public void setHelperItem(String helperItem) {
		this.helperItem = helperItem;		
	}

	public String getHelperItem() {
		if (this.helperItem == null) { 
			return null;
		}
		return this.helperItem;
	}

	public LearnItemsList getLearnItemsList() {
		return learnItemsList;
	}

	public void setLearnItemsList(LearnItemsList learnItemsList) {
		this.learnItemsList = learnItemsList;
	}

}
