package com.hybridtheory.mozzarella.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.springframework.transaction.annotation.Transactional;

public class GenericDaoJpa<T> implements GenericDao<T> {

	private Class<T> type;
	
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	public EntityManager entityManager;
	
	public GenericDaoJpa(Class<T> type) {
		super();
		this.type = type;
	}
	
	@Transactional(readOnly=true)
	public T get(Long id) {
		if (id == null) {
			return null;
		} else {
			return entityManager.find(type, id);
		}
	}

	@Transactional(readOnly=true)
	public List<T> getAll() {
		return entityManager.createQuery("select o from" + type.getName() + "o").getResultList();	
	}

	public void save(T object) {
		entityManager.persist(object);
	}

	public void delete(T object) {
		entityManager.remove(object);
	}
}