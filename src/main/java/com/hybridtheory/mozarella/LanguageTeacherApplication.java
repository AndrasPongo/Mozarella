package com.hybridtheory.mozarella;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LanguageTeacherApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LanguageTeacherApplication.class);

	public static void main(String[] args) {
		
		LOGGER.info("Starting LanguageTeacherApplication");
		SpringApplication.run(LanguageTeacherApplication.class, args);
		
		
		
	}

}
