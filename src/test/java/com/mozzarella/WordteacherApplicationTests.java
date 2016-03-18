package com.mozzarella;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mozzarella.WordteacherApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WordteacherApplication.class)
@WebAppConfiguration
public class WordteacherApplicationTests {

	@Test
	public void contextLoads() {
	}

}
