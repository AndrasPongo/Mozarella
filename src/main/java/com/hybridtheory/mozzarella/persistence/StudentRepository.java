package com.hybridtheory.mozzarella.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hybridtheory.mozarella.users.Student;

public class StudentRepository implements JpaRepository<Student, Integer> {

	@Autowired
	private EntityManager entityManager;
	
	public StudentRepository() {

	    // Keep the EntityManager around to used from the newly introduced methods.
		//this.entityManager = entityManager;
	}
	
	@Override
	public Page findAll(Pageable arg0) {
		
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Integer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Student arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends Student> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(Integer arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Student findOne(Integer arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Student> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInBatch(Iterable<Student> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Student> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> findAll(Iterable<Integer> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Student getOne(Integer id) {
		return entityManager.find(Student.class,id);
	}

	@Override
	public <S extends Student> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Student> S saveAndFlush(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void create(Student s){
		entityManager.persist(s);
	}

}
