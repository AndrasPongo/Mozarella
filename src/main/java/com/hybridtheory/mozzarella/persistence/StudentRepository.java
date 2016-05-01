package com.hybridtheory.mozzarella.persistence;

import org.springframework.data.repository.CrudRepository;

import com.hybridtheory.mozarella.users.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {

	
}
