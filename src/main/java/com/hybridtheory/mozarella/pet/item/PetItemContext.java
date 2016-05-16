package com.hybridtheory.mozarella.pet.item;

public interface PetItemContext {

	public Iterable<PetItemFactory<PetItem>> getPetItemFactories(UpdateCategory category);
}
