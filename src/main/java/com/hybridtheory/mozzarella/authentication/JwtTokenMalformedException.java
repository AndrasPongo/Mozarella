package com.hybridtheory.mozzarella.authentication;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenMalformedException extends AuthenticationException {
	JwtTokenMalformedException(String str){
		super(str);
	}
}
