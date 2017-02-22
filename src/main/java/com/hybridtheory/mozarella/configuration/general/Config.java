package com.hybridtheory.mozarella.configuration.general;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hybridtheory.mozarella.eventhandling.EventEmitter;
import com.hybridtheory.mozarella.users.StudentFactory;
import com.hybridtheory.mozarella.wordteacher.priority.LatestResultsBasedPriorityCalculator;
import com.hybridtheory.mozarella.wordteacher.priority.PriorityCalculator;
import com.hybridtheory.mozarella.wordteacher.teacher.ItemPrioritizer;
import com.hybridtheory.mozarella.wordteacher.teacher.ItemPrioritizerImpl1;
import com.hybridtheory.mozzarella.authentication.JwtUtil;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages={"com.hybridtheory.mozarella.api,com.hybridtheory.mozarella.persistence,com.hybridtheory.mozarella.eventhandling"})
@EnableJpaRepositories(basePackages={"com.hybridtheory.mozarella.persistence"})
public class Config{

	@Bean
	public ItemPrioritizer itemPrioritizer(){
		return new ItemPrioritizerImpl1();
	}
	
	@Bean
	protected PasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public StudentFactory studentFactory(){
		return new StudentFactory();
	}
	  
	@Bean 
	JwtUtil jwtUtil(){
		return new JwtUtil();
	}
	
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
    
    @Bean
    public EventEmitter emitter(){
    	return new EventEmitter();
    }
    
    @Bean 
    PriorityCalculator priorityCalculator(){
    	return new LatestResultsBasedPriorityCalculator(3);
    }
}