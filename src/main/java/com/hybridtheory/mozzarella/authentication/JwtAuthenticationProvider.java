package com.hybridtheory.mozzarella.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.hybridtheory.mozarella.users.Student;

public class JwtAuthenticationProvider implements AuthenticationProvider {

	@Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean supports(Class<?> authentication) {
        //return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    	return true;
    }

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = jwtAuthenticationToken.getToken();

        Student parsedUser = jwtUtil.parseToken(token);

        if (parsedUser == null) {
            throw new JwtTokenMalformedException("JWT token is not valid");
        }
        
        if(!parsedUser.isActivated()){
        	throw new DisabledException("User account is not activated");
        }

        //List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole());

        return jwtAuthenticationToken;
	}

}