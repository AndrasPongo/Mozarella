package com.hybridtheory.mozzarella.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hybridtheory.mozarella.LanguageTeacherApplication;
import com.hybridtheory.mozarella.users.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {LanguageTeacherApplication.class})
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class StudentApiIT {   
  private static final String STUDENT_RESOURCE = "/students/{id}";
  private static final Student student = new Student();


  @Value("${local.server.port}")
  private int serverPort;
  private Student firstStudent = new Student();
  private Student secondStudent = new Student();
  
  @Before
  public void setUp() {
    
    
  }
  
  @Test
  public void addItemShouldReturnSavedItem() {
    //given()
    //  .body(THIRD_ITEM)
    //  .contentType(ContentType.JSON)
    //.when()
    //  .post(ITEMS_RESOURCE)
    //.then()
    //  .statusCode(HttpStatus.SC_OK)
    //  .body(DESCRIPTION_FIELD, is(THIRD_ITEM_DESCRIPTION))
    //  .body(CHECKED_FIELD, is(false));
  }
} 