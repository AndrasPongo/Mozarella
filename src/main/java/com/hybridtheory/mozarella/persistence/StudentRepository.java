package com.hybridtheory.mozarella.persistence;

import org.springframework.data.repository.CrudRepository;

import com.hybridtheory.mozarella.users.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {

		Student findByName(String name);
}
