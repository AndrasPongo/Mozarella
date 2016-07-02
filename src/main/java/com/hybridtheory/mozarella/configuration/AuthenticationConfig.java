package com.hybridtheory.mozarella.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.hybridtheory.mozzarella.authentication.JwtAuthenticationFilter;
import com.hybridtheory.mozzarella.authentication.JwtAuthenticationProvider;
import com.hybridtheory.mozzarella.authentication.JwtUtil;
import com.hybridtheory.mozzarella.authentication.RestAuthenticationEntryPoint;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
  
		/*@Bean
	  public MethodSecurityService methodSecurityService() {
	    return new MethodSecurityServiceImpl()
	  }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .csrf().disable()
            .authorizeRequests().antMatchers("/api/**").authenticated()
        .and()
	        .exceptionHandling()
	        .authenticationEntryPoint(authenticationEntryPoint())
        .and()
            .addFilterBefore(authenticationFilter(),BasicAuthenticationFilter.class)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
  
  @Bean
  AuthenticationProvider authenticationProvider(){
	  return new JwtAuthenticationProvider();
  }
  
  @Bean 
  AuthenticationEntryPoint authenticationEntryPoint(){
	  return new RestAuthenticationEntryPoint();
  }
  
  @Bean
  JwtAuthenticationFilter authenticationFilter(){
	  return new JwtAuthenticationFilter(authenticationManager);
  }
  
  @Bean 
  JwtUtil jwtUtil(){
	  return new JwtUtil();
  }
  
}