package com.hybridtheory.mozarella.pet.item;

import java.util.function.Function;

import com.hybridtheory.mozarella.pet.Pet;
import com.hybridtheory.mozarella.users.Student;

public class PetItemFactory<T extends PetItem> {
	
	private final Class petClass;
	private final Function<Student,Integer> itemLevelCalculator;
	private final String nameOfItem;
	private final UpdateCategory updateCategory;
	
	
	public PetItemFactory(Class<T> petItemClass, Class petClass, String nameOfItem, UpdateCategory updateCategory, Function<Student,Integer> itemLevelCalculator){
		this.petClass = petClass;
		this.nameOfItem = nameOfItem;
		this.updateCategory = updateCategory;
		this.itemLevelCalculator = itemLevelCalculator;
	}
	
	private Boolean isCompatible(Pet p){
		return p.getClass().equals(petClass);
	}
	
	public PetItem createPetItem(Student s){
		
		if(!this.isCompatible(s.getPet())){
			return PetItem.nullPetItem;
		}
		
		Integer level = itemLevelCalculator.apply(s);
		
		if(level>-1){
			return new PetItem(nameOfItem);
		} else {
			return PetItem.nullPetItem;
		}
		
	}

	public UpdateCategory getUpdateCategory() {
		return updateCategory;
	}

	public Class getPetClass() {
		return petClass;
	}
	
	
}
