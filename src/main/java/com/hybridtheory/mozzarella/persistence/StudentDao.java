package com.hybridtheory.mozzarella.persistence;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;

import com.hybridtheory.mozarella.users.Student;

public interface StudentDao extends GenericDao<Student>{

	public Student getStudentByName(String name) throws EntityNotFoundException;
	public Student authenticateStudent(String username, String password) throws AuthenticationException;

}