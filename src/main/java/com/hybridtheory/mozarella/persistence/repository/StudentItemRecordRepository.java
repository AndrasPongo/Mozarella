package com.hybridtheory.mozarella.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.StudentItemRecord;

public interface StudentItemRecordRepository extends JpaRepository<StudentItemRecord,Integer> {
	
	@Query("select r from StudentItemRecord r where r.student.id = :studentId and r.learnItem.id = :learnItemId")
	public StudentItemRecord getStudentItemRecordForStudentAndLearnItemList(@Param("studentId") Integer studentId, @Param("learnItemId") Integer learnItemId);
}