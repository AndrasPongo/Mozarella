package com.hybridtheory.mozzarella.api;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hybridtheory.mozarella.LanguageTeacherApplication;
import com.hybridtheory.mozarella.persistence.repository.LearnItemListRepository;
import com.hybridtheory.mozarella.persistence.repository.LearnItemRepository;
import com.hybridtheory.mozarella.persistence.repository.ResultRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentItemRecordRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentRepository;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;

public class LearnItemControllerIT extends ApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private LearnItemRepository learnItemRepository;
	
	@Autowired
	private LearnItemListRepository learnItemListRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentItemRecordRepository studentItemRecordRepository;
	
	@Autowired
	private ResultRepository resultRepository;

	private MockMvc mockMvc;

	private static String LEARNITEMRESOURCE = "/api/learnitems/{id}";
	private static String RESULTRESOURCE = "/api/students/{studentid}/learnitemlists/{listid}/results";

	private static final Logger LOGGER = LoggerFactory.getLogger(LearnItemControllerIT.class);
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void validateDeleteLearnItemAlsoDeletesResults() throws Exception {
		
		//given
		Student student = new Student();
		studentRepository.save(student);
		
		LearnItem learnItem = new LearnItem("exampleword", "exampletranslation");
		learnItemRepository.save(learnItem);
		
		Integer id = learnItem.getId();
		
		LearnItemList learnItemList = new LearnItemList();
		learnItemListRepository.save(learnItemList);
		learnItem.setLearnItemsList(learnItemList);
		
		learnItemRepository.save(learnItem);
		
		Result result = new Result(true, student, learnItem);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(result);
		
		//when
		mockMvc.perform(post(RESULTRESOURCE,student.getId(),learnItemList.getId())
				.contentType(MediaType.APPLICATION_JSON).content(jsonInString))
				.andExpect(status().isOk());
		
		Long resultCountBeforeDelete = resultRepository.count();
		
		mockMvc.perform(delete(LEARNITEMRESOURCE,learnItem.getId()))
		.andExpect(status().isOk());	
		
		//Thread.sleep(10000);
		
		//then
		LOGGER.debug("resultRepository.count() "+resultRepository.count());
		LOGGER.debug("resultRepository.count() "+resultCountBeforeDelete);
		assertTrue(resultRepository.count()==resultCountBeforeDelete-1);
	}

}
