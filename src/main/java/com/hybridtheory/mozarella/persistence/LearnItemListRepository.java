package com.hybridtheory.mozarella.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;

public interface LearnItemListRepository extends CrudRepository<LearnItemList,Integer> {
	
	public List<LearnItemList> findByLanguage(List<String> fromLanguages, String toLanguage);
	
}
