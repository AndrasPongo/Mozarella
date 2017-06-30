package com.hybridtheory.mozarella;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan({"com.hybridtheory.mozarella.configuration.general,com.hybridtheory.mozarella.configuration.noauthentication","com.hybridtheory.mozarella.eventHandling"})
public class ApplicationForTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LanguageTeacherApplication.class);

	public static void main(String[] args) {
		
		LOGGER.info("Starting LanguageTeacherApplication for integration testing (authentication turned off)");
		ApplicationContext context = SpringApplication.run(ApplicationForTest.class, args);
		
	} 

}
