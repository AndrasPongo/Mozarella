package com.hybridtheory.mozarella.api;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hybridtheory.mozarella.LanguageTeacherApplication;
import com.hybridtheory.mozarella.persistence.LearnItemListRepositoryCustom;
import com.hybridtheory.mozarella.persistence.repository.LearnItemListRepository;
import com.hybridtheory.mozarella.persistence.repository.LearnItemRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentRepository;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;

@RestController
public class LearnItemController {
	
	@Autowired
    private LearnItemListRepositoryCustom learnItemListRepositoryCustom;
	
	@Autowired
    private LearnItemListRepository learnItemListRepository;
	
	@Autowired
    private LearnItemRepository learnItemRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LearnItemController.class);
	
    @RequestMapping(value="/api/learnitems/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteItemAuthorized(@RequestHeader("Authorization") Optional<String> authHeader, @PathVariable("id") Integer id) {
    	LOGGER.debug("start of learnItem delete call");
    	LearnItem toDelete = learnItemRepository.findOne(id);
    	learnItemRepository.delete(toDelete);
    	
    	LOGGER.debug("end of learnItem delete call");
    	
    	return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
