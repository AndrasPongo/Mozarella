package com.hybridtheory.mozarella.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
@Order(1)
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
            .authorizeRequests()
            .antMatchers("/login**").permitAll()
            .antMatchers("/registration**").permitAll()
            .antMatchers("/static/**").permitAll()
            .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
            .antMatchers("/**").access("hasRole('ROLE_USER')")
        .and()
	        .exceptionHandling()
	        .authenticationEntryPoint(authenticationEntryPoint())
        .and()
            .addFilterBefore(authenticationFilter(),BasicAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/login**").permitAll()
            .antMatchers("/registration**").permitAll()
            .antMatchers("/static/**").permitAll()
            .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
            .antMatchers("/**").access("hasRole('ROLE_USER')")
         .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ;
    }
    
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/login")
            .antMatchers("/registration");
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