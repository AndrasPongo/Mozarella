package com.hybridtheory.mozzarella.api;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemsList;
import com.hybridtheory.mozzarella.persistence.StudentRepository;

public class StudentControllerIT extends ApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private StudentRepository repository;
    
    private Student student1 = new Student();
    private Student student2 = new Student();
    private LearnItemsList learnItemsList;
    private LearnItemsList learnItemsList2;
    private static String STUDENT1NAME = "Anakin Skywalker";
    private static String STUDENT2NAME = "Qui Gon Jinn";

    private MockMvc mockMvc;
    
    private static String learnitemlistresource = "/students/{ids}/learnitemslists/{learnItemListIds}";
    private static String learnitemresource = "/students/{ids}/learnitemslists/{learnItemListIds}/learnItems";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
        
        learnItemsList = new LearnItemsList("learnItemList1");
        learnItemsList2 = new LearnItemsList("learnItemList2");
        
        student1.setName(STUDENT1NAME);
        student1.associateWithLearnItemsList(learnItemsList);
        student1.associateWithLearnItemsList(learnItemsList2);
        
        student2.setName(STUDENT2NAME);
        student2.associateWithLearnItemsList(learnItemsList);
        
        repository.save(student1);
    }

    @Test
    public void validate_get_student() throws Exception {

        mockMvc.perform(get("/students/"+student1.getId()))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name").value(STUDENT1NAME))
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
    	mockMvc.perform(get(learnitemresource,student1.getId(),learnItemsList.getId()+","+learnItemsList2.getId()));
    }

}