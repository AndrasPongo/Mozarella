package com.hybridtheory.mozarella.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;

public interface LearnItemRepository extends JpaRepository<LearnItem,Integer> {

	@Query("select distinct l.learnItems from Student s join s.learnItemManager m join m.learnItemLists l join l.learnItems i join i.results r where l.id in :listIds and s.id = :studentId and s = r.user order by r.resultscontainer.priority")
	public List<LearnItem> getLearnItemsForStudent(@Param("studentId") Integer studentId, @Param("listIds") List<Integer> learnItemListids, Pageable pageable);

}
