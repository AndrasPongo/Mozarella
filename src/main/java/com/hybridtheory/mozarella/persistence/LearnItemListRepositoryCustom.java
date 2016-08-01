package com.hybridtheory.mozarella.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;

public interface LearnItemListRepositoryCustom {
	public List<LearnItemList> findBasedOnLanguage(List<String> fromLanguages, String toLanguage);
}
