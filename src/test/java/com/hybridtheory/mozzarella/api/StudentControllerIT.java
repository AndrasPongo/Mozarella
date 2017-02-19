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

import com.hybridtheory.mozarella.persistence.repository.LearnItemListRepository;
import com.hybridtheory.mozarella.persistence.repository.SomeEntityRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentItemRecordRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentRepository;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.SomeEntity;

public class StudentControllerIT extends ApplicationTests {

    private static final String studentsResource = "/api/students/";

	@Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private StudentItemRecordRepository studentItemRecordRepository;
    
    @Autowired
    private LearnItemListRepository learnItemListRepository;
    
    @Autowired
    private SomeEntityRepository someEntityRepository;
    
    private static Student student1 = new Student();
    private static Student student2 = new Student();
    private static LearnItemList learnItemsList;
    private static LearnItemList learnItemsList2;
    private static String STUDENT1NAME = "Anakin Skywalker";
    private static String STUDENT2NAME = "Qui Gon Jinn";
    private static LearnItem learnitem1 = new LearnItem("exampleword","exampletranslation");
    private static LearnItem learnitem2 = new LearnItem("exampleword2","exampletranslation2");
    private static LearnItem learnitem3 = new LearnItem("exampleword3","exampletranslation3");
    private static LearnItem learnitem4 = new LearnItem("exampleword4","exampletranslation4");

    private MockMvc mockMvc;
    
    private static Boolean initializedFlag = false;
    
    @Before
    public void setup() {
    	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    	
    	if(!initializedFlag){
            
            learnItemsList = new LearnItemList("learnItemList1");
            
            learnItemsList.addLearnItem(new LearnItem("someexpression","translation"));
            learnItemsList.addLearnItem(new LearnItem("someexpression3","translation2"));
            
            LearnItem learnItem1 = new LearnItem("someexpression","translation");
            LearnItem learnItem2 = new LearnItem("someexpression","translation");
            		
            learnItemListRepository.save(learnItemsList);
            
            learnItemsList2 = new LearnItemList("learnItemList2");
            learnItemListRepository.save(learnItemsList2);
            
            student1.setName(STUDENT1NAME);
            student1 = studentRepository.save(student1);
            //TODO: we only associate with already persisted learnItemLists!
            //check for the id in the controller method!
            
            //as for this method, call for "persist manually"
            student1.associateWithLearnItemsList(learnItemsList);
            student1.associateWithLearnItemsList(learnItemsList2);
            
            student2.setName(STUDENT2NAME);
            student2 = studentRepository.save(student2);
            student2.associateWithLearnItemsList(learnItemsList);
            
            //learnItemsList.addLearnItem(learnitem1);
            //learnItemsList.addLearnItem(learnitem2);
            //learnItemsList.addLearnItem(learnitem3);
            //	learnItemsList.addLearnItem(learnitem4);
            
            /*for(Integer i = 0; i<1000; i++){
            	learnItemsList.addLearnItem(new Word("exampleword"+i,"exampletranslation"+i));
            }*/
            
            studentRepository.save(student1);
            studentRepository.save(student2);
            
            initializedFlag = true;
    	}    
    }

    @Test
    public void validateGetStudent() throws Exception {

        mockMvc.perform(get(studentsResource+student1.getId()))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].name").value(STUDENT1NAME))
                .andExpect(jsonPath("$").isArray());
    }
    
    @Test
    public void validateGetStudentAgain() throws Exception {
    	mockMvc.perform(get(studentsResource+student1.getId())).andExpect(status().isOk());
    }
    
    @Test
    public void validateGetStudentsByName() throws Exception {
    	mockMvc.perform(get("/api/students").param("name", STUDENT1NAME))
    	.andExpect(status().isOk())
    	.andExpect(jsonPath("$[0].name").value(STUDENT1NAME))
        .andExpect(jsonPath("$").isArray());
    }
    
    @Test
    public void validateGetMultipleStudents() throws Exception{
        mockMvc.perform(get("/api/students"))
        .andExpect(status().isOk())
        .andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$",hasSize(Math.toIntExact(studentRepository.count()))));
    }
    
    @Test
    public void validateGetLearnableLearnItems() throws Exception{
    	
    	String path = "/api/students/"+student2.getId()+"/learnitemlists/"+learnItemsList.getId()+"/learnitems";
    	System.out.println(learnItemsList.getNumberOfLearnItemsInList());
    	
    	mockMvc.perform(get(path).param("count", "10"))
        .andExpect(status().isOk())
        .andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$",hasSize(Math.toIntExact(learnItemsList.getNumberOfLearnItemsInList()))));
    }
    
    @Test
    public void validateCantGetLearnableLearnItemsAgain() throws Exception{
    	
    	String path = "/api/students/"+student2.getId()+"/learnitemlists/"+learnItemsList.getId()+"/learnitems";
    	System.out.println(learnItemsList.getNumberOfLearnItemsInList());
    	
    	mockMvc.perform(get(path).param("count", "10"))
        .andExpect(status().isOk())
        .andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$",hasSize(Math.toIntExact(0))));
    }
    
    @Test
    public void test(){
    	SomeEntity entity = new SomeEntity();
        long start = System.currentTimeMillis();
        someEntityRepository.save(entity);
        long end = System.currentTimeMillis();
        System.out.println("cost time:" + (end - start));
    }

}