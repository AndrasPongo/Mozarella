package com.hybridtheory.mozzarella.authentication;

import java.nio.charset.Charset;
import java.util.Base64;

public class UsernamePasswordDecoder {
	public static String[] decodeUserNamePassword(String authorizationHeaderContent){
		if (authorizationHeaderContent != null && authorizationHeaderContent.startsWith("Basic")) {
	        // Authorization: Basic base64credentials
	        String base64Credentials = authorizationHeaderContent.substring("Basic".length()).trim();
	        String credentials = new String(Base64.getDecoder().decode(base64Credentials),
	                Charset.forName("UTF-8"));
	        // credentials = username:password
	        return credentials.split(":",2);
	}
		return null;
	}
}

