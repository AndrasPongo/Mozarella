package com.hybridtheory.mozzarella.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import com.hybridtheory.mozzarella.api.StudentApi;

@Configuration
//@EnableJpaRepositories("com.hybridtheory.mozzarella.persistence")
@EnableTransactionManagement
@ComponentScan(basePackages={"com.hybridtheory.mozzarella"})
@EnableJpaRepositories(basePackages={"com.hybridtheory.mozzarella.persistence"})
public class StudentConfig {

//	@Bean
//	public StudentApi studentApi() {
//		return new StudentApi();
//	}
/*
	@Bean
	public JpaVendorAdapter jpaVendorAdapter()
	{
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setGenerateDdl(false);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");		
		
		
		return adapter;
	}

	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		DataSource ds =  DataSourceBuilder.create().build();
		return ds;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());		
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerFactory.setPackagesToScan("com.hybridtheory.mozarella.users");		
		return entityManagerFactory;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
		JpaTransactionManager transactionManager = new JpaTransactionManager(emf);

		return transactionManager;
	}
*/
}