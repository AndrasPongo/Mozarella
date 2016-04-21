package com.hybridtheory.mozzarella.persistence;

import java.util.List;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;

import com.hybridtheory.mozarella.users.Student;

public class StudentDaoJpa extends GenericDaoJpa<Student> implements StudentDao {

	public StudentDaoJpa(Class<Student> type) {
		super(Student.class);
	}

	public Student getStudentByName(String name) throws EntityNotFoundException, DataAccessException {
		List<Student> results = null;
		Query query = entityManager.createQuery("from Student as s where s.name = :name");
		query.setParameter("name", name);
		results = query.getResultList();
		if (results == null || results.size() <= 0) {
			throw new EntityNotFoundException(name + "not found");
		} else {
			return results.get(0);
		}
	}

	public Student authenticateStudent(String username, String password) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}
}