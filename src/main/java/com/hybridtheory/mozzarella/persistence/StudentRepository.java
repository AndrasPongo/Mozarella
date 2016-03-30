package com.hybridtheory.mozzarella.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hybridtheory.mozarella.users.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
