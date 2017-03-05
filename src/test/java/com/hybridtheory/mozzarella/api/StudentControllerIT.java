package com.hybridtheory.mozzarella.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is; 

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hybridtheory.mozarella.api.ResultController;
import com.hybridtheory.mozarella.eventhandling.EventEmitter;
import com.hybridtheory.mozarella.persistence.repository.LearnItemListRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentItemRecordRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentRepository;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentControllerIT extends ApplicationTests {

    private static final String STUDENTSRESOURCE = "/api/students/";
    private static final String RESULTRESOURCE = "/api/results";
    private static final String STUDENTLEARNITEMLISTSRESOURCE = "/api/students/{studentid}/learnitemlists";
    private static final String STUDENTLEARNITEMLISTRESOURCE = "/api/students/{studentid}/learnitemlists/{listid}";
    
    private static final String STUDENT1NAME = "Anakin Skywalker";
    private static final String STUDENT2NAME = "Qui Gon Jinn";
    
    
	@Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private StudentItemRecordRepository studentItemRecordRepository;
    
    @Autowired
    private LearnItemListRepository learnItemListRepository;
    
    @Autowired
    private ResultController resultController;
    
    private static Student student1 = new Student();
    private static Student student2 = new Student();
    private static LearnItemList learnItemsList;
    private static LearnItemList learnItemsList2;
   
    private static LearnItem learnItem = new LearnItem("someexpression","translation");
    private static LearnItem learnItem2 = new LearnItem("exampleword2","exampletranslation2");

    private MockMvc mockMvc;
    
    private static Boolean initializedFlag = false;
    
    @Before
    public void setup() {
    	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    	
    	if(!initializedFlag){
            
            learnItemsList = new LearnItemList("learnItemList1");
            
            learnItemsList.addLearnItem(learnItem);
            learnItemsList.addLearnItem(learnItem2);
            		
            learnItemListRepository.save(learnItemsList);
            
            learnItemsList2 = new LearnItemList("learnItemList2");
            learnItemListRepository.save(learnItemsList2);
            
            student1.setName(STUDENT1NAME);
            student1 = studentRepository.save(student1);

            student1.associateWithLearnItemsList(learnItemsList);
            student1.associateWithLearnItemsList(learnItemsList2);
            
            student2.setName(STUDENT2NAME);
            student2 = studentRepository.save(student2);
            student2.associateWithLearnItemsList(learnItemsList);
            
            studentRepository.save(student1);
            studentRepository.save(student2);
            
            initializedFlag = true;
    	}    
    }

    @Test
    public void validateGetStudent() throws Exception {

        mockMvc.perform(get(STUDENTSRESOURCE+student1.getId()))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].name").value(STUDENT1NAME))
                .andExpect(jsonPath("$").isArray());
    }
    
    @Test
    public void validateGetStudentAgain() throws Exception {
    	mockMvc.perform(get(STUDENTSRESOURCE+student1.getId())).andExpect(status().isOk());
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
    public void test1ValidateGetLearnableLearnItems() throws Exception{
    	
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
    public void test2ValidateCantGetLearnableLearnItemsAgain() throws Exception{
    	
    	String path = "/api/students/"+student2.getId()+"/learnitemlists/"+learnItemsList.getId()+"/learnitems";
    	
    	
    	System.out.println("PATH: "+path);
    	
    	Result result = new Result(true, student2, learnItem);
    	
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(result);
		
		//when
		mockMvc.perform(post(RESULTRESOURCE).contentType(MediaType.APPLICATION_JSON).content(jsonInString))
		.andExpect(status().isOk());
		
		Field f = resultController.getClass().getDeclaredField("eventEmitter");
		f.setAccessible(true);
		EventEmitter emitter = (EventEmitter) f.get(resultController);
		
		ExecutorService executorService = emitter.getExecutorService();
		executorService.shutdown();
		
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		
    	
    	mockMvc.perform(get(path).param("count", "10"))
        .andExpect(status().isOk())
        .andExpect(
               content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$",hasSize(Math.toIntExact(1))));
    } 
    
    @Test
    @Transactional
    public void testAssociateWithLearnItemList() throws Exception{
    	Student student = new Student();
    	LearnItemList learnItemList = new LearnItemList("aglast");
    	
    	studentRepository.save(student);
    	learnItemListRepository.save(learnItemList);
    	
    	ObjectMapper mapper = new ObjectMapper();
    	String jsonInString = mapper.writeValueAsString(learnItemList);
    	
    	System.out.println("before size() "+student.getLearnItemLists().size());
    	
    	mockMvc.perform(post(STUDENTLEARNITEMLISTSRESOURCE,student.getId()).contentType(MediaType.APPLICATION_JSON).content(jsonInString))
        .andExpect(status().isOk());
    	
    	student = studentRepository.findOne(student.getId());
    	
    	System.out.println("after size() "+student.getLearnItemLists().size());
    	
    	assertTrue(student.getLearnItemLists().contains(learnItemList));
    }
    
    @Test
    @Transactional
    public void testGetLearnItemLists() throws Exception{
    	Student student = new Student();
    	LearnItemList learnItemList = new LearnItemList("aglast");
    	
    	student.associateWithLearnItemsList(learnItemList);
    	
    	studentRepository.save(student);
    	learnItemListRepository.save(learnItemList);
    	
    	mockMvc.perform(get(STUDENTLEARNITEMLISTSRESOURCE,student.getId())
    			.param("pagenumber", "0")
    			.param("pagesize", "10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$",hasSize(Math.toIntExact(1))))
        .andExpect(jsonPath("$[0].name").value(is(learnItemList.getName())));
    }
    
    @Test
    @Transactional
    public void testDisAssociateWithLearnItemList() throws Exception{
    	Student student = new Student();
    	LearnItemList learnItemList = new LearnItemList();
    	
    	studentRepository.save(student);
    	learnItemListRepository.save(learnItemList);
    	
    	student.associateWithLearnItemsList(learnItemList);
    	
    	ObjectMapper mapper = new ObjectMapper();
    	String jsonInString = mapper.writeValueAsString(learnItemList);
    	
    	mockMvc.perform(delete(STUDENTLEARNITEMLISTRESOURCE,student.getId(),learnItemList.getId()))
        .andExpect(status().isOk());
    	
    	assertFalse(student.getLearnItemLists().contains(learnItemList));
    }

}