package com.hybridtheory.mozarella.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hybridtheory.mozarella.persistence.LearnItemListRepositoryCustom;
import com.hybridtheory.mozarella.persistence.repository.LearnItemListRepository;
import com.hybridtheory.mozarella.persistence.repository.LearnItemRepository;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;

@RestController
public class LearnItemListController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
    private LearnItemListRepositoryCustom learnItemListRepositoryCustom;
	
	@Autowired
    private LearnItemListRepository learnItemListRepository;
	
	@Autowired
    private LearnItemRepository learnItemRepository;

    @RequestMapping(value="/api/learnitemlists", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Iterable<LearnItemList>> getLearnItemLists(@RequestHeader("Authorization") Optional<String> authHeader,
    																@RequestParam(value="fromLanguages") Optional<List<String>> fromLanguages, 
    																@RequestParam(value="toLanguage") Optional<String> toLanguage,
    																@RequestParam("pagenumber") Integer pageNumber, 
    																@RequestParam("pagesize") Integer pageSize) {
    	LOGGER.debug("start of learnItemLists call");
    	
    	Page<LearnItemList> lists;
    	lists = learnItemListRepositoryCustom.findBasedOnLanguage(fromLanguages,toLanguage,new PageRequest(pageNumber,pageSize));
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.add("X-total-count", Long.toString(lists.getTotalElements()));
        headers.add("Access-Control-Expose-Headers", "X-total-count");
    	
    	ResponseEntity<Iterable<LearnItemList>> result = new ResponseEntity<Iterable<LearnItemList>>(lists.getContent(),headers,HttpStatus.OK);
    	
    	LOGGER.debug("end of learnItemLists call");
    	
    	return result;
    }
	
    @RequestMapping(value="/api/learnitemlists/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Iterable<LearnItemList>> getLearnItemListAuthorized(@RequestHeader("Authorization") Optional<String> authHeader, @PathVariable("id") Integer id) {
    	LOGGER.debug("start of learnItemList call");
    	LearnItemList list = learnItemListRepository.findOne(id);
    	List<LearnItemList> result = new ArrayList<LearnItemList>();
    	result.add(list);
    	
    	LOGGER.debug("end of learnItemList call");
    	return new ResponseEntity<Iterable<LearnItemList>>(result,HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/learnitemlists/{id}/learnitems", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<LearnItem>> getItemsAuthorized(@RequestHeader("Authorization") Optional<String> authHeader, @PathVariable("id") Integer id, @RequestParam("pagenumber") Optional<Integer> pageNumber, @RequestParam("pagesize") Optional<Integer> pageSize) {
    	if(pageNumber.isPresent() && pageSize.isPresent()){
    		LOGGER.debug("start of learnItems call");
    		
    		Page<LearnItem> learnItems = learnItemRepository.getLearnItemsForLearnItemList(id, new PageRequest(pageNumber.get(),pageSize.get()));
    		
    		HttpHeaders headers = new HttpHeaders();
            
    		headers.add("X-total-count", Long.toString(learnItems.getTotalElements()));
            headers.add("Access-Control-Expose-Headers", "X-total-count");
            
            LOGGER.debug("end of learnItems call");
            
    		return new ResponseEntity<List<LearnItem>>(learnItems.getContent(),headers,HttpStatus.OK);
    	} else {
    		//TODO: implement useful logic here
        	return new ResponseEntity<List<LearnItem>>(learnItemRepository.findAll(new PageRequest(0,10)).getContent(),HttpStatus.OK);
    	}
    }
    
    @RequestMapping(value="/api/learnitemlists/{id}/learnitems", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<LearnItem>> addItemsAuthorized(@RequestHeader("Authorization") Optional<String> authHeader, @PathVariable("id") Integer id, @RequestBody List<LearnItem> learnItemsToPersist) {
    	LearnItemList listToAssociateWith = learnItemListRepository.findOne(id);
    	
    	learnItemsToPersist.stream().forEach(learnItem -> learnItem.setLearnItemsList(listToAssociateWith));
    	List<LearnItem> persistedLearnItems = learnItemRepository.save(learnItemsToPersist);
    	
    	return new ResponseEntity<List<LearnItem>>(persistedLearnItems,HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/learnitemlists", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LearnItemList>> saveLearnItemList(@RequestBody LearnItemList learnItemListToPersist) {
    	LearnItemList savedLearnItemList = learnItemListRepository.save(learnItemListToPersist);
    	List<LearnItemList> result = new ArrayList<LearnItemList>();
    	result.add(savedLearnItemList);
    	
    	return new ResponseEntity<List<LearnItemList>>(result,HttpStatus.OK);
    }
}
