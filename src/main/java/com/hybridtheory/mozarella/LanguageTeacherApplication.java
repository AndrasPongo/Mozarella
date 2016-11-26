package com.hybridtheory.mozarella;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan({"com.hybridtheory.mozarella.configuration.general","com.hybridtheory.mozarella.configuration.authentication"})
public class LanguageTeacherApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LanguageTeacherApplication.class);

	public static void main(String[] args) {
		
		LOGGER.info("Starting LanguageTeacherApplication1");
		ApplicationContext context = SpringApplication.run(LanguageTeacherApplication.class, args);
		
	} 

}
