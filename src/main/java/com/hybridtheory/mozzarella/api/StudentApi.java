package com.hybridtheory.mozzarella.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozzarella.persistence.StudentRepository;
 
@Component
@Path("students")
public class StudentApi {
 
	@Autowired
    StudentRepository studentRepository;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt() {
        return "Got it!";
    }
    
    @POST
    public Response createNewUser() {
        // TODO create the new user properly
    	Student student = new Student("some student name");
    	Student saved = studentRepository.save(student);
    	
    	return Response.status(200).entity(saved.toString()).build();
    }
}
