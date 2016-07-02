package com.hybridtheory.mozarella.persistence.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;

public interface LearnItemRepository extends JpaRepository<LearnItem,Integer> {
	
	@Query("select distinct i from Student s join s.learnItemManager m join m.learnItemLists l join l.learnItems i, StudentItemRecord r where s.id = :studentId and l.id in:listIds and r.student = s order by r.priority")
	public List<LearnItem> getPracticedLearnItemsForStudent(@Param("studentId") Integer studentId, @Param("listIds") List<Integer> learnItemListIds,Pageable pageable);
	
	@Query("select i from Student s join s.learnItemManager m join m.learnItemLists l join l.learnItems i where s.id = :studentId and l.id in :listIds and not exists (select r from StudentItemRecord r where r.student = s)")
	public List<LearnItem> getNewItemsForStudent(@Param("studentId") Integer studentId, @Param("listIds") List<Integer> learnItemListids,Pageable pageable);
	
	public default List<LearnItem> getAllLearnItemsForStudent(@Param("studentId") Integer studentId, @Param("listIds") List<Integer> learnItemListids,Pageable pageable){
		List<LearnItem> toReturn = getPracticedLearnItemsForStudent(studentId, learnItemListids, pageable);	
		if(toReturn.size()<pageable.getPageSize()){
			toReturn.addAll(getNewItemsForStudent(studentId, learnItemListids, new PageRequest(0,pageable.getPageSize()-toReturn.size())));
		}
		
		return toReturn;
	}

	@Query("select i from LearnItemList l join l.learnItems i")
	public List<LearnItem> getLearnItemsForLearnItemList(Integer id, PageRequest pageRequest);
}
