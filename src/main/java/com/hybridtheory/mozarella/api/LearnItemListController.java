package com.hybridtheory.mozarella.api;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<Iterable<LearnItemList>> getLearnItemLists(@RequestParam(value="fromLanguages", required = false) List<String> fromLanguages, @RequestParam(value="toLanguage", required=false) String toLanguage) {
    	//TODO: implement pagination
    	Iterable<LearnItemList> lists;
    	if(fromLanguages != null && toLanguage != null){
    		lists = learnItemListRepositoryCustom.findBasedOnLanguage(fromLanguages,toLanguage);
    	} else {
    		lists = learnItemListRepository.findAll();
    	}
    	
    	return new ResponseEntity<Iterable<LearnItemList>>(lists,HttpStatus.OK);
    }
	
    @RequestMapping(value="/api/learnitemlists/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Iterable<LearnItemList>> getLearnItemList(@PathVariable("id") Integer id) {
    	LearnItemList list = learnItemListRepository.findOne(id);
    	List<LearnItemList> result = new ArrayList<LearnItemList>();
    	result.add(list);
    	
    	return new ResponseEntity<Iterable<LearnItemList>>(result,HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/learnitemlists/{id}/learnitems", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<LearnItem>> getItems(@PathVariable("id") Integer id, @RequestParam("pagenumber") Integer pageNumber, @RequestParam("pagesize") Integer pageSize) {
    	return new ResponseEntity<List<LearnItem>>(learnItemRepository.getLearnItemsForLearnItemList(id, new PageRequest(pageNumber,pageSize)),HttpStatus.OK);
    }
    
    @RequestMapping(value="/api/learnitemlists/{id}/learnitems", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity addItems(@RequestBody List<LearnItem> learnItemsToPersist) {
    	learnItemRepository.save(learnItemsToPersist);
    	
    	return new ResponseEntity(HttpStatus.OK);
    }
}
