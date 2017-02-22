package com.hybridtheory.mozarella.persistence.repository;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;

public interface LearnItemRepository extends JpaRepository<LearnItem,Integer> {
	
	@Query("select distinct i from Student s join s.learnItemLists l join l.learnItems i, StudentItemRecord r where s.id = :studentId and l.id in:listIds and r.student = s and r.learnItem = i and not (r.lastModified between :startDateTime and :endDateTime) order by r.priority")
	public List<LearnItem> getPracticedLearnItemsForStudent(@Param("studentId") Integer studentId, @Param("listIds") List<Integer> learnItemListIds, @Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);
	
	@Query("select i from Student s join s.learnItemLists l join l.learnItems i where s.id = :studentId and l.id in :listIds and not exists (select r from StudentItemRecord r where r.student = s and r.learnItem = i)")
	public List<LearnItem> getNewItemsForStudent(@Param("studentId") Integer studentId, @Param("listIds") List<Integer> learnItemListids,Pageable pageable);
	
	public default List<LearnItem> getAllLearnItemsForStudent(@Param("studentId") Integer studentId, @Param("listIds") List<Integer> learnItemListids,Pageable pageable){
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime oneDayAgo = now.minusDays(1);
		
		List<LearnItem> toReturn = getPracticedLearnItemsForStudent(studentId, learnItemListids, oneDayAgo, now);	
		System.out.println("toReturn size: "+toReturn.size()+"learnItemListids :"+Arrays.toString(learnItemListids.toArray())+" studentId: "+studentId);
		
		if(toReturn.size()<pageable.getPageSize()){
			toReturn.addAll(getNewItemsForStudent(studentId, learnItemListids, new PageRequest(0,pageable.getPageSize()-toReturn.size())));
		}
		
		return toReturn;
	}

	@Query("select i from LearnItemList l join l.learnItems i where l.id = :id")
	public Page<LearnItem> getLearnItemsForLearnItemList(@Param("id")Integer id, Pageable pageable);
	
	@Query("select count(i) from LearnItemList l join l.learnItems i where l.id = :id")
	public Integer getNumberOfLearnItemsForLearnItemList(@Param("id")Integer id);
}
