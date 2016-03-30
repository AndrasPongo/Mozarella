package com.hybridtheory.mozarella;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozzarella.persistence.StudentRepository;

@SpringBootApplication
@ComponentScan("com.hybridtheory.mozzarella.configuration")
public class LanguageTeacherApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LanguageTeacherApplication.class);
	//private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);;

	public static void main(String[] args) {
		
		LOGGER.info("Starting LanguageTeacherApplication");
		ApplicationContext context = SpringApplication.run(LanguageTeacherApplication.class, args);
		
		
	}

}
