package com.hybridtheory.mozarella.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;

public interface LearnItemListRepositoryCustom {
	public Page<LearnItemList> findBasedOnLanguage(Optional<String> name, Optional<String> fromLanguages, Optional<String> toLanguage, Pageable pageable);
}
