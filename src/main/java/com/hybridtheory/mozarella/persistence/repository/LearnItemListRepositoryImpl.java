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

}
