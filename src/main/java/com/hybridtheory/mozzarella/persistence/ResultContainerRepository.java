package com.hybridtheory.mozzarella.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.ResultContainer;

public interface ResultContainerRepository extends JpaRepository<ResultContainer, Integer> {
	
	@Query("select r from Student s join s.learnItemManager m join m.results r where s.id = :studentId and r.learnItem.id = :learnItemId")
	public List<ResultContainer> getResultContainerForStudentAndLearnItem(@Param("studentId") Integer studentId, @Param("learnItemId")  Integer learnItemId);
	
}