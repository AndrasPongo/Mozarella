package com.hybridtheory.mozarella.persistence;

import java.util.List;

public interface GenericDao<T> {
	public T get(Long id);
	public List<T> getAll();
	public void save(T object);
	public void delete(T object);
}