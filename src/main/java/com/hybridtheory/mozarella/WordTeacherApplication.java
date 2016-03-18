package com.hybridtheory.mozarella;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.mozzarella.WordteacherApplication;

@SpringBootApplication
public class WordTeacherApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WordTeacherApplication.class);

	public static void main(String[] args) {
		
		LOGGER.info("Starting WordTeacherApplication");
		SpringApplication.run(WordteacherApplication.class, args);
		
		
		
	}

}
