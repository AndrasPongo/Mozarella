package com.hybridtheory.mozarella.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hybridtheory.mozzarella.authentication.JwtUtil;

@Configuration
public class JwtUtilConfiguration {
	
	@Bean
	JwtUtil jwtUtil(){
		JwtUtil jwtUtil = new JwtUtil();
		org.springframework.test.util.ReflectionTestUtils.setField(jwtUtil, "secret", "some secret");
		
		return jwtUtil;
	}

}
