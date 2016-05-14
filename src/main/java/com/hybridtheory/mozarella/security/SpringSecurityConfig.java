package com.hybridtheory.mozarella.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	protected AuthenticationProvider authenticationProvider(){
		return new UsernamePasswordAuthenticationProvider();
	}
	
	@Bean
	protected PasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	  auth.authenticationProvider(authenticationProvider());
	}
	
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable()
	    .authorizeRequests()
	        .antMatchers("/").access("hasRole('STUDENT')")
	        .antMatchers("/students/**").permitAll();
	}
	


//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private RestAuthenticationEntryPoint authenticationEntryPoint;
//
//    @Autowired
//    private AccessDeniedHandler accessDeniedHandler;
//	
//    @Bean(name="myAuthenticationManager")
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//	    http
//			.authorizeRequests()
//			    .antMatchers("/students","/about").permitAll()
//			    .and()
//			.sessionManagement()
//			    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			    .and()
//			.exceptionHandling()
//			    .authenticationEntryPoint(authenticationEntryPoint)
//			    .accessDeniedHandler(accessDeniedHandler)
//			    .and();
//
//        //TODO: Custom Filters
//    }
//	  
//	    @Override
//	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	        auth
//	                .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//	    }


	    
/*
	  @Override
	  public void configure(WebSecurity web) throws Exception {
	    web
	      .ignoring()
	         .antMatchers("/resources/**");
	  }
	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http
	      .authorizeRequests()
	        .antMatchers("/signup","/about").permitAll()
	        .antMatchers("/admin/**").hasRole("ADMIN")
	        .anyRequest().authenticated() // 7
	        .and()
	    .formLogin()
	        .loginPage("/login")
	        .permitAll();
	  }
	  
	  */

}