package com.hybridtheory.mozarella.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages={"com.hybridtheory.mozarella"})
@EnableJpaRepositories(basePackages={"com.hybridtheory.mozarella.persistence"})
public class Config {

}