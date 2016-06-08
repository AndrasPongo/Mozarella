package com.hybridtheory.mozzarella.api;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hybridtheory.mozarella.persistence.ResultContainerRepository;
import com.hybridtheory.mozarella.persistence.StudentRepository;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Word;

import static org.junit.Assert.*;

public class StudentControllerIT extends ApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private StudentRepository repository;
    
    @Autowired
    private ResultContainerRepository resultContainerRepository;
    
    private static Student student1 = new Student();
    private static Student student2 = new Student();
    private static LearnItemList learnItemsList;
    private static LearnItemList learnItemsList2;
    private static String STUDENT1NAME = "Anakin Skywalker";
    private static String STUDENT2NAME = "Qui Gon Jinn";
    private static LearnItem learnitem1 = new Word("exampleword","exampletranslation");
    private static LearnItem learnitem2 = new Word("exampleword2","exampletranslation2");
    private static LearnItem learnitem3 = new Word("exampleword3","exampletranslation3");
    private static LearnItem learnitem4 = new Word("exampleword4","exampletranslation4");

    private MockMvc mockMvc;
    
    private static String learnitemlistresource = "/students/{ids}/learnitemslists/{learnItemListIds}";
    private static String learnitemresource = "/students/{ids}/learnitemslists/{learnItemListIds}/learnitems";
    private static String resultresource = "/students/{id}/learnitems/{learnItemId}/results";
    
    private static Boolean initializedFlag = false;
    
    @Before
    public void setup() {
    	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    	
    	if(!initializedFlag){
            
            learnItemsList = new LearnItemList("learnItemList1");
            learnItemsList2 = new LearnItemList("learnItemList2");
            
            student1.setName(STUDENT1NAME);
            student1.associateWithLearnItemsList(learnItemsList);
            student1.associateWithLearnItemsList(learnItemsList2);
            
            student2.setName(STUDENT2NAME);
            student2.associateWithLearnItemsList(learnItemsList);
            
            learnItemsList.addLearnItem(learnitem1);
            learnItemsList.addLearnItem(learnitem2);
            learnItemsList.addLearnItem(learnitem3);
            learnItemsList.addLearnItem(learnitem4);
            
            repository.save(student1);
            
            initializedFlag = true;
    	}    
    }

    @Test
    public void validate_get_student() throws Exception {

        mockMvc.perform(get("/students/"+student1.getId()))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].name").value(STUDENT1NAME))
                .andExpect(jsonPath("$").isArray())
        		.andExpect(jsonPath("$",hasSize(1)));
    }
    
    @Test
    public void validate_get_multiple_students() throws Exception{
        mockMvc.perform(get("/students"))
        .andExpect(status().isOk())
        .andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$",hasSize(Math.toIntExact(repository.count()))));
    }
    
    @Test
    public void validate_get_learnitemsLists() throws Exception{
    	mockMvc.perform(get(learnitemlistresource,student1.getId(),learnItemsList.getId()+","+learnItemsList2.getId()))
    	.andExpect(status().isOk())
        .andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$").isArray());
    }
    
    @Test
    public void validate_get_learnitems() throws Exception{
    	mockMvc.perform(get(learnitemresource,student1.getId(),learnItemsList.getId()+","+learnItemsList2.getId()).param("number", "100"))
    	.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$",hasSize(4)));
    }
    
    @Test
    public void validate_result_added() throws Exception{
    	mockMvc.perform(post(resultresource,student1.getId(),learnitem1.getId()).param("result", "true"));
    }

}