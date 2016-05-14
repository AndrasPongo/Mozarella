package com.hybridtheory.mozarella.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozzarella.persistence.StudentRepository;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private StudentRepository studentRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

	http.csrf().disable()
	    .authorizeRequests()
	        .antMatchers("/").access("hasRole('STUDENT')")
	        .antMatchers("/students/**").permitAll();
//	    .and()
//	        .formLogin()
//	        .loginPage("/login")
//	        .failureUrl("/login?error=true");
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
    	
        Student newStudent = new Student();
        newStudent.initialize("username");
        studentRepository.save(newStudent);
    	
		auth
		.userDetailsService(new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				Iterable<Student> studentsWithIds = studentRepository.findAll();
				
		    	for (Student student: studentsWithIds) {
		    		if (student.getName() == username) {
				    	return studentRepository.findOne(student.getId());
		    		}
		    	}
		          throw new UsernameNotFoundException("User '" + username + "' not found.");
			}
		});		
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