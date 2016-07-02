package com.hybridtheory.mozzarella.authentication;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenMissingException extends AuthenticationException {
	public JwtTokenMissingException(String str){
		super(str);
	}
}
