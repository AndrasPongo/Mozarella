package com.hybridtheory.mozzarella.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hybridtheory.mozarella.LanguageTeacherApplication;
import com.hybridtheory.mozarella.persistence.repository.StudentRepository;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthenticationTest extends AuthenticationApplicationTests {
	
	@Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private StudentRepository repository;

    private MockMvc mockMvc;
    
    private static String loginresource = "/login";
    private static String registrationresource = "/register";
    private static String learnitemlistresource = "/api/learnitemlists/{id}";
    
    private static String userPswd = "somePassword";
    private static String userName = "Anakin Skywalker";
    
    private String jwtToken;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageTeacherApplication.class);
    
    @Before
    public void setup() {
    	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
    			.apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

    }  
    
    @Test
    public void t1_validate_cant_access_resource_before_authentication() throws Exception{
    	 mockMvc.perform(get(learnitemlistresource,1))
    	.andExpect(status().isUnauthorized());
    }
      
    @Test
    public void t2_validate_register_with_username_password() throws Exception{
    	userName = userName+repository.count();
    	
    	String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64((userName+":"+userPswd).getBytes()));
    	
    	MvcResult result = this.mockMvc.perform(post(registrationresource)
    				.header("Authorization", basicDigestHeaderValue)
    				.accept(MediaType.APPLICATION_JSON))
    				.andExpect(status().isOk())
    				.andReturn();
    	
    	jwtToken = result.getResponse().getContentAsString();
    	
    	LOGGER.info("t2:"+result.toString());
    }
    
    @Test
    public void t3_validate_can_access_resource_with_token() throws Exception{
    	String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64((userName+":"+userPswd).getBytes()));
    	
    	MvcResult result = this.mockMvc.perform(post(loginresource)
				.header("Authorization", basicDigestHeaderValue)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	
    	jwtToken = result.getResponse().getContentAsString();
    	
    	LOGGER.info("t3:"+result.toString());
    	
    	mockMvc.perform(get(learnitemlistresource,1)
    	.header("Authorization", "Bearer "+jwtToken))
    	.andExpect(status().isOk());
    }

}
