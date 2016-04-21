package com.hybridtheory.mozzarella.api;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hybridtheory.mozarella.LanguageTeacherApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LanguageTeacherApplication.class)
@WebAppConfiguration
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

}