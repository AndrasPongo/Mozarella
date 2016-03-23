package com.hybridtheory.mozarella;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozzarella.persistence.StudentRepository;

@SpringBootApplication
public class LanguageTeacherApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LanguageTeacherApplication.class);
	//private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);;

	public static void main(String[] args) {
		
		LOGGER.info("Starting LanguageTeacherApplication");
		ApplicationContext context = SpringApplication.run(LanguageTeacherApplication.class, args);
		
		StudentRepository studentRepo = context.getBean(StudentRepository.class);
		
		LOGGER.info("Persisting student");
		studentRepo.create(new Student("StudentExample"));
		
		Integer studentId=0;
		
		LOGGER.info("Student with id "+studentId+" "+studentRepo.findOne(studentId));
		
	
	}

}
