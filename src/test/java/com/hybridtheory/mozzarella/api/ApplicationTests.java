package com.hybridtheory.mozzarella.api;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hybridtheory.mozarella.ApplicationForTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationForTest.class)
@TestPropertySource("classpath:test.properties")
@WebAppConfiguration
public class ApplicationTests {
	@Test
	public void contextLoads() {
	}
}