package com.hybridtheory.mozarella.api;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hybridtheory.mozarella.eventhandling.EventEmitter;
import com.hybridtheory.mozarella.eventhandling.result.NewResultAvailableEvent;
import com.hybridtheory.mozarella.persistence.repository.StudentRepository;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;

@RestController
public class ResultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ResultController.class);
	
	@Autowired
	private StudentRepository studentRepository;
	
    @Autowired
    private EventEmitter eventEmitter;
	
    @RequestMapping(value="/api/results", method=RequestMethod.POST)
    public ResponseEntity postResult(@RequestBody Result result) {
    
    	eventEmitter.publish(new NewResultAvailableEvent(result));
    	
    	return new ResponseEntity(HttpStatus.OK);
    }
	
}

