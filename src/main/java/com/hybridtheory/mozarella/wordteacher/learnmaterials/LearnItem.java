package com.hybridtheory.mozarella.wordteacher.learnmaterials;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class LearnItem implements Comparable<LearnItem>, Serializable {

	private static final long serialVersionUID = 7526471111622776111L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@OneToMany(mappedBy = "learnItem", cascade = { CascadeType.ALL })
	protected List<StudentItemRecord> studentItemRecords;

	@OneToMany(mappedBy = "learnItem", cascade = { CascadeType.ALL })
	protected List<Result> results;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	protected LearnItemList learnItemList;

	protected String idDescriptor;
	protected String text;

	@ElementCollection
	protected List<String> translations = new ArrayList<String>();
	protected Double priority;
	protected Integer strength = 0;
	protected String pictureReference;

	private String helperItem;

	@Transient
	Boolean alreadyPracticed; // to be filled during learnItem retrieval

	public LearnItem() {
	}

	public LearnItem(String text, String translation) {
		this.text = text;
		this.translations = new ArrayList<String>();
		this.translations.add(translation);
	}

	public LearnItem(String text, List<String> translations) {
		this.text = text;
		this.translations = translations;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public void addTranslation(String translation) {
		this.translations.add(translation);
	}

	public String getTranslation() {
		return getTranslation(0);
	}

	@JsonIgnore
	public String getTranslation(Integer index) {
		if (translations.size() < index + 1) {
			return null;
		}

		return translations.get(index);
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	@JsonIgnore
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

	public List<String> getTranslations() {
		return translations;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public int compareTo(LearnItem other) {
		System.out.println("learnItem compareTo, this id: "+id);
		System.out.println("learnItem compareTo, this text: "+text);
		System.out.println("learnItem compareTo, other id: "+other.id);
		System.out.println("learnItem compareTo, other text: "+other.text);
		return this.text.compareTo(other.text);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getAlreadyPracticed() {
		return alreadyPracticed;
	}

	public void setAlreadyPracticed(Boolean alreadyPracticed) {
		this.alreadyPracticed = alreadyPracticed;
	}

}