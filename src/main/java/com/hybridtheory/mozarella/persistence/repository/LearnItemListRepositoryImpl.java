package com.hybridtheory.mozarella.persistence.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.hybridtheory.mozarella.persistence.LearnItemListRepositoryCustom;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;

public class LearnItemListRepositoryImpl implements LearnItemListRepositoryCustom {

	@Autowired
	LearnItemListRepository learnItemListRepository;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public Page<LearnItemList> findBasedOnLanguage(Optional<List<String>> fromLanguages, Optional<String> toLanguage, Pageable pageable) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<LearnItemList> criteria = builder.createQuery(LearnItemList.class);
		Root<LearnItemList> learnItemListRoot = criteria.from(LearnItemList.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(fromLanguages.isPresent() && fromLanguages.get().size()>0){
			predicates.add(learnItemListRoot.get("fromLanguage").in(fromLanguages));
		}
		if(toLanguage.isPresent()){
			predicates.add(builder.equal(learnItemListRoot.get("toLanguage"), toLanguage.get()));
		}
		
		criteria.select(learnItemListRoot);
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		Query query = entityManager.createQuery( criteria );
		List<LearnItemList> list = query.getResultList();
		int totalRows = query.getResultList().size();
		
		 query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		 query.setMaxResults(pageable.getPageSize());
		
		Page<LearnItemList> page = new PageImpl<LearnItemList>(query.getResultList(), pageable, totalRows);
	    return page;
	}

}
