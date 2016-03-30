package com.hybridtheory.mozzarella.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.hybridtheory.mozzarella.persistence")
public class Config {
	
	//@Bean
	//public JpaRepository studentRepository(){
	//	return new StudentRepository();
	//}
	
}
