package com.hybridtheory.mozarella.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {

	@Query("select r from Result r where r.student.id = :studentId and r.learnItem.id = :learnItemId order by r.time")
	List<Result> getLastResultsForStudentAndLearnItem(@Param("studentId") Integer studentId, @Param("learnItemId") Integer learnItemId, Pageable pageable);

}
