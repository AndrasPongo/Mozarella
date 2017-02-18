package com.hybridtheory.mozzarella.api;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hybridtheory.mozarella.persistence.repository.LearnItemListRepository;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;

public class LearnItemListControllerIT extends ApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private LearnItemListRepository repository;

	private static LearnItemList learnItemsList;
	private static LearnItemList learnItemsList2;
	private static LearnItemList learnItemsListToSave;
	private static LearnItem learnitem1 = new LearnItem("exampleword", "exampletranslation");
	private static LearnItem learnitem2 = new LearnItem("exampleword2", "exampletranslation2");
	private static LearnItem learnitem3 = new LearnItem("exampleword3", "exampletranslation3");
	private static LearnItem learnitem4 = new LearnItem("exampleword4", "exampletranslation4");

	private MockMvc mockMvc;

	private static String learnitemlistsresource = "/api/learnitemlists";
	private static String learnitemlistresource = "/api/learnitemlists/{id}";
	private static String learnitemresource = "/api/learnitemlists/{id}/learnitems";

	private static String TOSAVENAME = "tosavename";
	private static final String LEARNITEMLIST1NAME = "learnItemList1";

	private static Boolean initializedFlag = false;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		if (!initializedFlag) {

			learnItemsList = new LearnItemList(LEARNITEMLIST1NAME);
			learnItemsList2 = new LearnItemList("learnItemList2");

			learnItemsList.addLearnItem(learnitem1);
			learnItemsList.addLearnItem(learnitem2);
			learnItemsList.addLearnItem(learnitem3);
			learnItemsList.addLearnItem(learnitem4);
			
			learnItemsListToSave = new LearnItemList(TOSAVENAME);

			repository.save(learnItemsList);
			repository.save(learnItemsList2);

			initializedFlag = true;
		}
	}

	@Test
	public void validate_create_learnitemlists() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(learnItemsListToSave);
		
		mockMvc.perform(post(learnitemlistsresource).contentType(MediaType.APPLICATION_JSON).content(jsonInString))
		.andExpect(status().isOk());
	}
	
	@Test
	public void validate_get_learnitemlist() throws Exception {
		mockMvc.perform(get(learnitemlistresource,learnItemsList.getId())).andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].name").value(LEARNITEMLIST1NAME));
	}

}
