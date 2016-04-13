package com.hybridtheory.mozzarella.api;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hybridtheory.mozarella.LanguageTeacherApplication;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozzarella.persistence.StudentRepository;
import static com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.RestAssured;
import static org.hamcrest.Matchers.*;
import com.jayway.restassured.http.ContentType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {LanguageTeacherApplication.class})
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class StudentApiIT {   
  private static final String STUDENTS_RESOURCE = "/students/{id}";
  private static final String STUDENT_RESOURCE = "/students/{id}";
  private static final String NAME_FIELD = "name";
  private static final String FIRST_STUDENT_NAME = "Anakin";
  private static final String SECOND_STUDENT_NAME = "Emperor";
  private static final String THIRD_STUDENT_NAME = "JarJar";
  private static final Student student = new Student();


  @Value("${local.server.port}")
  private int serverPort;
  private Student firstStudent = new Student();
  private Student secondStudent = new Student();
  private Student thirdStudent = new Student();
  
  @Autowired
  private StudentRepository repository;
  private Student student1, student2;
  
  @Before
  public void setUp() {
	  firstStudent.initialize(FIRST_STUDENT_NAME);
	  secondStudent.initialize(SECOND_STUDENT_NAME);
	  thirdStudent.initialize(THIRD_STUDENT_NAME);
	  //repository.deleteAll();
	  student1 = repository.save(firstStudent);
	  student2 = repository.save(secondStudent);
	  RestAssured.port = serverPort;
  }
  
 /* TODO: write this after we the details are clear
  @Test
  public void addItemShouldReturnSavedItem() {
    given()
      .body(thirdStudent)
      .contentType(ContentType.JSON)
    .when()
      .post(STUDENTS_RESOURCE)
    .then()
      .statusCode(HttpStatus.SC_OK)
      .body(NAME_FIELD, is(THIRD_STUDENT_NAME));
  }*/
  
  @Test
  public void getItemShouldReturnSavedItem() {
	  when()
	    .get(STUDENTS_RESOURCE,student1.getId())
	  .then()
	    .statusCode(HttpStatus.SC_OK)
	    .body(NAME_FIELD, hasItems(FIRST_STUDENT_NAME));
	}
  
} 