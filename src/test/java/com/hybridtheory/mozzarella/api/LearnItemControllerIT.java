package com.hybridtheory.mozzarella.api;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hybridtheory.mozarella.persistence.repository.LearnItemListRepository;
import com.hybridtheory.mozarella.persistence.repository.LearnItemRepository;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;

public class LearnItemControllerIT extends ApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private LearnItemRepository learnItemRepository;
	
	@Autowired
	private LearnItemListRepository learnItemListRepository;

	private MockMvc mockMvc;

	private static String LEARNITEMRESOURCE = "/api/learnitems/{id}";

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void validateCreateLearnItemLists() throws Exception {
		LearnItem learnItem = new LearnItem("exampleword", "exampletranslation");
		learnItemRepository.save(learnItem);
		
		Integer id = learnItem.getId();
		
		LearnItemList learnItemList = new LearnItemList();
		learnItemListRepository.save(learnItemList);
		
		learnItemList.addLearnItem(learnItem);
		
		mockMvc.perform(delete(LEARNITEMRESOURCE,learnItem.getId()))
		.andExpect(status().isOk());	
		
		LearnItem saved = learnItemRepository.findOne(id);
		assertTrue(saved==null);
	}

}
