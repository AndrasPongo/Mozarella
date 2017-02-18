package com.hybridtheory.mozarella.serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class AuthorityDeserializer extends JsonDeserializer<List<GrantedAuthority>> {

    @Override
    public List<GrantedAuthority> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.getCurrentToken() == JsonToken.START_ARRAY) {
	            List<GrantedAuthority> authorities = new ArrayList<>();
	            while(jsonParser.nextToken() != JsonToken.END_ARRAY) {
	            	String value = jsonParser.getValueAsString();
	            	if(value!=null){
	            		authorities.add(new SimpleGrantedAuthority(value));
	            	}
	            }
	            return authorities;
	        }
	        throw deserializationContext.mappingException("Expected Permissions list");
    }
}
