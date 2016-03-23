package com.hybridtheory.mozzarella.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hybridtheory.mozzarella.persistence.StudentRepository;

@Configuration
public class JavaConfig {
	
	@Bean
	public JpaRepository studentRepository(){
		return new StudentRepository();
	}
	
}
