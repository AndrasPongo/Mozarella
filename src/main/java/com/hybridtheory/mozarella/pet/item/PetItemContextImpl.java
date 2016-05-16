package com.hybridtheory.mozarella.pet.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hybridtheory.mozarella.pet.Pet;
import com.hybridtheory.mozarella.pet.cubefish.CubeFish;

public class PetItemContextImpl implements PetItemContext{
	
	private List<PetItemFactory<PetItem>> petItemFactories = new ArrayList<PetItemFactory<PetItem>>();
	
	public PetItemContextImpl(){
		
		petItemFactories
		.add(new PetItemFactory<PetItem>(
				PetItem.class,
				CubeFish.class,
				"Castle",
				UpdateCategory.LEARNINGSESSIONEND,
				(s)->{
					if(s.isEnabled()){
						return 1;
					}
					return -1;
				}));
	
	}
	
	@Override
	public Iterable<PetItemFactory<PetItem>> getPetItemFactories(UpdateCategory category) {
		return petItemFactories.stream()
				.filter(f->f.getUpdateCategory().equals(category))
				.collect(Collectors.toList());
	}
	
	public Iterable<PetItemFactory<PetItem>> getPetItemFactoriesForPet(Pet pet) {
		return petItemFactories.stream()
				.filter(f->f.getPetClass().isAssignableFrom(pet.getClass()))
				.collect(Collectors.toList());
	}
	
	
}
