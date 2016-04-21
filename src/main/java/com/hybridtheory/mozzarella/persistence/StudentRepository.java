package com.hybridtheory.mozzarella.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.hybridtheory.mozarella.users.Student;

@Transactional
public interface StudentRepository extends CrudRepository<Student, Integer> {

}
