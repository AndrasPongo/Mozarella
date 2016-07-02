package com.hybridtheory.mozarella.persistence.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import com.hybridtheory.mozarella.persistence.LearnItemListRepositoryCustom;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;

public class LearnItemListRepositoryImpl implements LearnItemListRepositoryCustom {

	@Autowired
	LearnItemListRepository learnItemListRepository;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public List<LearnItemList> findBasedOnLanguage(List<String> fromLanguages, String toLanguage) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<LearnItemList> criteria = builder.createQuery(LearnItemList.class);
		Root<LearnItemList> learnItemListRoot = criteria.from(LearnItemList.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(fromLanguages.size()>0){
			predicates.add(learnItemListRoot.get("fromLanguage").in(fromLanguages));
		}
		predicates.add(builder.equal(learnItemListRoot.get("toLanguage"), toLanguage));
		
		criteria.select(learnItemListRoot);
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return entityManager.createQuery( criteria ).getResultList();
	}
	
	@Override
	public <S extends LearnItemList> S save(S entity) {
		return learnItemListRepository.save(entity);
	}

	@Override
	public <S extends LearnItemList> Iterable<S> save(Iterable<S> entities) {
		return learnItemListRepository.save(entities);
	}

	@Override
	public LearnItemList findOne(Integer id) {
		return learnItemListRepository.findOne(id);
	}

	@Override
	public boolean exists(Integer id) {
		return learnItemListRepository.exists(id);
	}

	@Override
	public Iterable<LearnItemList> findAll() {
		return learnItemListRepository.findAll();
	}

	@Override
	public Iterable<LearnItemList> findAll(Iterable<Integer> ids) {
		return learnItemListRepository.findAll(ids);
	}

	@Override
	public long count() {
		return learnItemListRepository.count();
	}

	@Override
	public void delete(Integer id) {
		learnItemListRepository.delete(id);
	}

	@Override
	public void delete(LearnItemList entity) {
		learnItemListRepository.delete(entity);
		
	}

	@Override
	public void delete(Iterable<? extends LearnItemList> entities) {
		learnItemListRepository.delete(entities);
		
	}

	@Override
	public void deleteAll() {
		learnItemListRepository.deleteAll();
		
	}

}
