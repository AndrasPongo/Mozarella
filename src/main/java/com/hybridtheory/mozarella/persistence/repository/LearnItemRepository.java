package com.hybridtheory.mozarella.persistence.repository;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hybridtheory.mozarella.api.LearnItemController;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;

public interface LearnItemRepository extends JpaRepository<LearnItem,Integer> {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(LearnItemController.class);
	
	@Query("select distinct i from Student s join s.learnItemLists l join l.learnItems i, StudentItemRecord r where s.id = :studentId and l.id in:listIds and r.student = s and r.learnItem = i and r.lastModified < :beforeDateTime order by r.priority")
	public List<LearnItem> getPracticedLearnItemsForStudent(@Param("studentId") Integer studentId, @Param("listIds") List<Integer> learnItemListIds, @Param("beforeDateTime") LocalDateTime beforeDateTime, Pageable pageable);
	
	@Query("select i from Student s join s.learnItemLists l join l.learnItems i where s.id = :studentId and l.id in :listIds and not exists (select r from StudentItemRecord r where r.student = s and r.learnItem = i)")
	public List<LearnItem> getNewItemsForStudent(@Param("studentId") Integer studentId, @Param("listIds") List<Integer> learnItemListids,Pageable pageable);
	
	public default List<LearnItem> getAllLearnItemsForStudent(@Param("studentId") Integer studentId, @Param("listIds") List<Integer> learnItemListids,Pageable pageable){
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime oneDayAgo = now.minusDays(1);
		
		List<LearnItem> toReturn = getPracticedLearnItemsForStudent(studentId, learnItemListids, oneDayAgo, pageable);	
		System.out.println("toReturn size: "+toReturn.size()+"learnItemListids :"+Arrays.toString(learnItemListids.toArray())+" studentId: "+studentId);
		
		toReturn.forEach(item->item.setAlreadyPracticed(true));
		
		//TODO test this properly
		if(toReturn.size()<pageable.getPageSize()){
			List<LearnItem> newItems = getNewItemsForStudent(studentId, learnItemListids, new PageRequest(0,pageable.getPageSize()-toReturn.size()));
			newItems.forEach(item -> {
				item.setAlreadyPracticed(false);
			});
			
			toReturn.addAll(newItems);
		}
		
		return toReturn;
	}

	@Query("select i from LearnItemList l join l.learnItems i where l.id = :id")
	public Page<LearnItem> getLearnItemsForLearnItemList(@Param("id")Integer id, Pageable pageable);
	
	@Query("select count(i) from LearnItemList l join l.learnItems i where l.id = :id")
	public Integer getNumberOfLearnItemsForLearnItemList(@Param("id")Integer id);
}
