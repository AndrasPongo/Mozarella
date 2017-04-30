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
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.hybridtheory.mozarella.persistence.LearnItemListRepositoryCustom;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LearnItemListRepositoryImpl implements LearnItemListRepositoryCustom {

	@Autowired
	LearnItemListRepository learnItemListRepository;
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public Page<LearnItemList> findBasedOnLanguage(Optional<String> name, Optional<String> fromLanguage, Optional<String> toLanguage, Pageable pageable) {
		
		Query query = createQuery(name,fromLanguage,toLanguage);
		
		List<LearnItemList> list = query.getResultList();
		int totalRows = query.getResultList().size();
		
		 query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		 query.setMaxResults(pageable.getPageSize());
		
		Page<LearnItemList> page = new PageImpl<LearnItemList>(query.getResultList(), pageable, totalRows);
		
	    return page;
	}
	
	private Query createQuery(Optional<String> name, Optional<String> fromLanguage, Optional<String> toLanguage){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		//Metamodel m = entityManager.getMetamodel();
		
		//EntityType<LearnItemList> LearnItemList_ = m.entity(LearnItemList.class);
		
		CriteriaQuery<LearnItemList> criteria = builder.createQuery(LearnItemList.class);
		Root<LearnItemList> learnItemListRoot = criteria.from(LearnItemList.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(fromLanguage.isPresent()){
			predicates.add(builder.equal(learnItemListRoot.<String>get("fromLanguageCode"), fromLanguage.get()));
		}
		if(toLanguage.isPresent()){
			predicates.add(builder.equal(learnItemListRoot.<String>get("toLanguageCode"), toLanguage.get()));
		}
		if(name.isPresent()){
			predicates.add(builder.like(learnItemListRoot.<String>get("name"), "%"+name.get()+"%"));
		}
		
		criteria.select(learnItemListRoot);
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return entityManager.createQuery( criteria );
	}

}
