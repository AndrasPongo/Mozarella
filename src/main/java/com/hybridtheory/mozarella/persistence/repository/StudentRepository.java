package com.hybridtheory.mozarella.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hybridtheory.mozarella.users.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	@Query("select s from Student s where s.name = :name")
	List<Student> findByName(@Param("name") String name);
}
