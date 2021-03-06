package com.hybridtheory.mozarella.configuration.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.hybridtheory.mozzarella.authentication.JwtAuthenticationFilter;
import com.hybridtheory.mozzarella.authentication.JwtAuthenticationProvider;
import com.hybridtheory.mozzarella.authentication.RestAuthenticationEntryPoint;
import com.hybridtheory.mozzarella.authentication.RestAuthenticationSuccessHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
@Order(1)
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AuthenticationManager authenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider())
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/register**").permitAll()
            .antMatchers("/static/**").permitAll()
            .antMatchers("/usernameavailable").permitAll()
            .antMatchers("/activate").permitAll()
            .antMatchers("/admin/**").authenticated()//.access("hasRole('ROLE_ADMIN')")
        .and()
	        .exceptionHandling()
	        .authenticationEntryPoint(authenticationEntryPoint())
        .and()
            .addFilterBefore(authenticationFilter(),BasicAuthenticationFilter.class)
            .authenticationProvider(authenticationProvider())
            .authorizeRequests()
         .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ;
    }
    
    public void configure(WebSecurity web) throws Exception {
        web
        	.ignoring()
            .antMatchers("/registration");
    }
    
	@Bean
	public FilterRegistrationBean corsFilter() {
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:3000");
		config.addAllowedOrigin("https://mozarella.herokuapp.com");
		config.addAllowedOrigin("https://www.mozarella.tech");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
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
	  try {
		return new JwtAuthenticationFilter(super.authenticationManagerBean(), successHandler());
	  } catch (Exception e) {
		return new JwtAuthenticationFilter(null,successHandler());
	  }
  }
  
  @Bean
  AuthenticationSuccessHandler successHandler(){
	  return new RestAuthenticationSuccessHandler();
  }
  
}