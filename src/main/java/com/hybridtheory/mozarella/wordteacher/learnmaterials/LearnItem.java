package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class LearnItem {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	@JsonIgnore
	protected LearnItemList learnItemList;
	
	protected String idDescriptor;
	protected String text;
	protected String translations;
	protected Double priority;
	protected Integer strength;
	protected String pictureReference;
	
	private String helperItem;
	
	public LearnItem(){
		
	}
	
	public int compareTo(LearnItem learnItem) {
		if (learnItem.getText().equals(this.text)) { 
			return 0;	
		} else {
			return -1;
		}
	}
	
	public void setId(String idDescriptor) {
		this.idDescriptor = idDescriptor;		
	}

	public void setText(String text) {
		this.text = text;		
	}

	public String getText() {
		return this.text;
	}

	public void addTranslation(String translation) {
		this.translations+=translation+";";
	}

	public String getTranslation(){
		return getTranslation(0);
	}
	
	public String getTranslation(Integer index) {
		if(this.translations.split(";").length>index){
			return translations.split(";")[index];
		}
		else {
			return "";
		}
	}

	public void removeAlternativeTranslation(String alternativeTranslation) {
		//if (alternativeTranslation == null || !translations.contains(alternativeTranslation)) {
		//	throw new IllegalArgumentException("Alternative translation doesn't exist for this Learn Item");
		//} else {
		//	this.translations.remove(this.translations.indexOf(alternativeTranslation));

		//}
	}

	public void setStrength(int strength) {
		this.strength = strength;		
	}

	public int getStrength() {
		return strength;
	}
	
	public void setPriority(Double priority) {
		this.priority = priority;		
	}
	
	public Double getPriority() {
		return priority;
	}

	public void setPictureReference(String pictureReference) {
		this.pictureReference = pictureReference;
	}

	public String getPictureReference() {
		return this.pictureReference;
	}

	public void setHelperItem(String helperItem) {
		this.helperItem = helperItem;		
	}

	public String getHelperItem() {
		return this.helperItem;
	}

	@JsonIgnore
	public LearnItemList getLearnItemsList() {
		return learnItemList;
	}

	@JsonIgnore
	public void setLearnItemsList(LearnItemList learnItemsList) {
		this.learnItemList = learnItemsList;
	}
	
	public String getTranslations(){
		return translations;
	}

	public Integer getId() {
		return id;
	}

}
