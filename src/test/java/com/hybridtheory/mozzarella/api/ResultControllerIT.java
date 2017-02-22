package com.hybridtheory.mozzarella.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hybridtheory.mozarella.api.ResultController;
import com.hybridtheory.mozarella.eventhandling.EventEmitter;
import com.hybridtheory.mozarella.persistence.repository.LearnItemRepository;
import com.hybridtheory.mozarella.persistence.repository.ResultRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentItemRecordRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentRepository;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.StudentItemRecord;

public class ResultControllerIT extends ApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private LearnItemRepository learnItemRepository;

	@Autowired
	private ResultController resultController;

	@Autowired
	private StudentItemRecordRepository studentItemRecordRepository;

	@Autowired
	ResultRepository resultRepository;

	private MockMvc mockMvc;
	private static Boolean initializedFlag = false;

	private static String resultresource = "/api/results";

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		if (!initializedFlag) {

		}
	}

	@Test
	public void validateNewResultIsStored() throws Exception {

		// given
		Student student = new Student();
		studentRepository.save(student);

		LearnItem learnItem = new LearnItem();
		learnItemRepository.save(learnItem);

		Result resultToSave = new Result(true, student, learnItem);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(resultToSave);

		// when
		mockMvc.perform(post(resultresource).contentType(MediaType.APPLICATION_JSON).content(jsonInString))
				.andExpect(status().isOk());

		Field f = resultController.getClass().getDeclaredField("eventEmitter");
		f.setAccessible(true);
		EventEmitter emitter = (EventEmitter) f.get(resultController);

		ExecutorService executorService = emitter.getExecutorService();
		executorService.shutdown();

		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

		List<Result> latestResults = resultRepository.getLastResultsForStudentAndLearnItem(student.getId(),
				learnItem.getId(), new PageRequest(0, 5));
		StudentItemRecord studentItemRecord = studentItemRecordRepository
				.getStudentItemRecordForStudentAndLearnItemList(student.getId(), learnItem.getId());

		// then
		assertEquals(latestResults.size(), 1);
		assertEquals(latestResults.get(0).getStudent().getId(), resultToSave.getStudent().getId());
		assertEquals(latestResults.get(0).getLearnItem().getId(), resultToSave.getLearnItem().getId());

		assertTrue(studentItemRecord != null);
		assertTrue(studentItemRecord.getPriority() != null);
	}
}
