package com.hybridtheory.mozarella.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;

public interface LearnItemListRepository extends CrudRepository<LearnItemList,Integer> {

	@Query("select l from Student s join s.learnItemLists l where s.id = :studentId")
	public List<LearnItemList> getLearnItemListsForStudent(@Param("studentId") Integer studentId, Pageable pageable);
	
}