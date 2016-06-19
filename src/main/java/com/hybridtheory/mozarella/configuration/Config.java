package com.hybridtheory.mozarella.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.hybridtheory.mozarella.wordteacher.teacher.ItemPrioritizer;
import com.hybridtheory.mozarella.wordteacher.teacher.ItemPrioritizerImpl1;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages={"com.hybridtheory.mozarella"})
@EnableJpaRepositories(basePackages={"com.hybridtheory.mozarella.persistence"})
public class Config {

	@Bean
	public ItemPrioritizer itemPrioritizer(){
		return new ItemPrioritizerImpl1();
	}
	
}