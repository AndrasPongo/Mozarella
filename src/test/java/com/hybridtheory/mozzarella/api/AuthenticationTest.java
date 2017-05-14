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
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozzarella.authentication.JwtUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthenticationTest extends AuthenticationApplicationTests {
	
	@Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private StudentRepository repository;
    
    @Autowired
    JwtUtil jwtUtil;

    private MockMvc mockMvc;
    
    private static final String loginresource = "/login";
    private static final String registrationresource = "/register";
    private static final String learnitemlistresource = "/api/learnitemlists/{id}";
    private static final String activationresource = "/activate";
    
    private static final String userPswd = "somePassword";
    private static String userName = "Anakin Skywalker";
    
    private String jwtToken;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageTeacherApplication.class);
    
    private static Student student;
    
    @Before
    public void setup() {
    	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
    			.apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

    }  
    
    @Test
    public void validateCantAccessResourceBeforeAuthentication() throws Exception{
    	 mockMvc.perform(get(learnitemlistresource,1))
    	.andExpect(status().isUnauthorized());
    }
      
    @Test
    public void test1ValidateRegisterWithUsernamePassword() throws Exception{
    	userName = userName+repository.count();
    	
    	String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64((userName+":"+userPswd).getBytes()));
    	
    	MvcResult result = this.mockMvc.perform(post(registrationresource)
    				.header("Authorization", basicDigestHeaderValue)
    				.accept(MediaType.APPLICATION_JSON))
    				.andExpect(status().isOk())
    				.andReturn();
    	
    	jwtToken = result.getResponse().getContentAsString();
    }
    
    @Test
    public void test2ValidateCantAccessResourceWithToken() throws Exception{
    	String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64((userName+":"+userPswd).getBytes()));
    	
    	MvcResult result = this.mockMvc.perform(post(loginresource)
				.header("Authorization", basicDigestHeaderValue)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	
    	jwtToken = result.getResponse().getContentAsString();
    	student = jwtUtil.parseToken(jwtToken); //to be used in the next test
    	
    	mockMvc.perform(get(learnitemlistresource,student.getId())
    	.header("Authorization", "Bearer "+jwtToken))
    	.andExpect(status().is4xxClientError()); //student is not activated yet
    }
    
    @Test
    public void test3ValidateCanAccessResourceWithTokenAfterActivation() throws Exception{
    	String basicDigestHeaderValue = "Basic " + new String(Base64.encodeBase64((userName+":"+userPswd).getBytes()));
    	
    	//1. activate account
    	MvcResult result = this.mockMvc.perform(get(activationresource)
    			.param("activationCode", student.getActivationCode())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
    	
    	//2. log in
    	result = this.mockMvc.perform(post(loginresource)
				.header("Authorization", basicDigestHeaderValue)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	
    	jwtToken = result.getResponse().getContentAsString();
    	
    	mockMvc.perform(get(learnitemlistresource,student.getId())
    	.header("Authorization", "Bearer "+jwtToken))
    	.andExpect(status().isOk());
    }

}