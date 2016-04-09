package com.hybridtheory.mozzarella.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozzarella.persistence.StudentRepository;
 
@Component
@Path("students")
public class StudentApi {
 
	@Autowired
    StudentRepository studentRepository;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getIt() {
    	//Pageable pageable = new Pageable();
    	//todo: make this pageable
        return studentRepository.findAll();
    }
    
    @POST
    public Response createNewUser() {
        // TODO create the new user properly
    	Student student = new Student();
    	student.setName("someStudent");
    	Student saved = studentRepository.save(student);
    	
    	return Response.status(200).entity(saved.toString()).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public List<LearnItem> getLearnItems(@PathParam("id") Integer id){
    	//return studentRepository.getOne(id).getOwnLearnItemLists().get(0);
    	return null;
    }
    
}
