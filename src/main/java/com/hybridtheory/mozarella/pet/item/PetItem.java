package com.hybridtheory.mozarella.pet.item;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PetItem {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer Id;
	private Integer position;
	private Boolean isChosen;
	private String name;
	private Integer level;
	
	@ElementCollection
	private List<String> pictureReferences;
	
	public PetItem(String name){
		this.name = name;
	}
	
	public List<String> getPictureReferences(){
		return pictureReferences;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPictureReferences(List<String> pictureReferences) {
		this.pictureReferences = pictureReferences;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getIsChosen() {
		return isChosen;
	}

	public void setIsChosen(Boolean isChosen) {
		this.isChosen = isChosen;
	}

	@Override
	public boolean equals(Object other){
		//TODO everything but level
		return false;
	}
	
	public static PetItem nullPetItem = new PetItem("non-acquired item");

}
