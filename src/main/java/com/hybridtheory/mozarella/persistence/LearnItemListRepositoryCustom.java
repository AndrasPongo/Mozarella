package com.hybridtheory.mozarella.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;

public interface LearnItemListRepositoryCustom {
	public List<LearnItemList> findBasedOnLanguage(List<String> fromLanguages, String toLanguage);

	public <S extends LearnItemList> S save(S entity);
	
	public <S extends LearnItemList> Iterable<S> save(Iterable<S> entities);

	public LearnItemList findOne(Integer id);

	public boolean exists(Integer id);

	public Iterable<LearnItemList> findAll();

	public Iterable<LearnItemList> findAll(Iterable<Integer> ids);

	public long count();

	public void delete(Integer id);

	public void delete(LearnItemList entity);

	public void delete(Iterable<? extends LearnItemList> entities);

	public void deleteAll();
}
