package com.hybridtheory.mozarella.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.hybridtheory.mozarella.eventhandling.EventEmitter;
import com.hybridtheory.mozarella.eventhandling.EventListener;
import com.hybridtheory.mozarella.eventhandling.event.Event;
import com.hybridtheory.mozarella.eventhandling.result.StudentRegisteredEvent;
import com.hybridtheory.mozarella.users.Student;
import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;
import com.sparkpost.model.AddressAttributes;
import com.sparkpost.model.RecipientAttributes;
import com.sparkpost.model.TemplateContentAttributes;
import com.sparkpost.model.TransmissionWithRecipientArray;
import com.sparkpost.model.responses.Response;
import com.sparkpost.resources.ResourceTransmissions;
import com.sparkpost.transport.RestConnection;

public class RegistrationEmailSender implements EventListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationEmailSender.class);
	
	@Value("${sparkpost.api.key}")
	private String apiKey;
	
	@Value("${sparkpost.api.url}")
	private String apiEndpoint;
	
    private Client client = new Client(apiKey);
    
    private EventEmitter emitter;
    
    @Value("${hostname}")
    private String host;
    
    @Autowired
	public RegistrationEmailSender(EventEmitter emitter){
		this.emitter = emitter;
		emitter.subscribe(this, StudentRegisteredEvent.class);
	}
	
	@Override
	public void beNotifiedOfEvent(Event e) {
		LOGGER.debug("api key: "+apiKey);
		
		StudentRegisteredEvent studentRegisteredEvent = (StudentRegisteredEvent) e;
		
		//TODO: send email verification mail
		try {
			sendEmail("registration@mozarella.tech", studentRegisteredEvent.getRegisteredStudent(), studentRegisteredEvent.getRegisteredStudent().getEmail());
		} catch (SparkPostException e1) {
			e1.printStackTrace();
			LOGGER.error(e1.getMessage());
		}
	}
	
	private void sendEmail(String from, Student student, String email) throws SparkPostException {
	    TransmissionWithRecipientArray transmission = new TransmissionWithRecipientArray();

	    // Populate Recipients
	    List<RecipientAttributes> recipientArray = new ArrayList<RecipientAttributes>();
	    List<String> recipients = new ArrayList<String>();
		recipients.add(student.getEmail());
	    
	    for (String recipient : recipients) {
	    RecipientAttributes recipientAttribs = new RecipientAttributes();
	        recipientAttribs.setAddress(new AddressAttributes(recipient));
	        recipientArray.add(recipientAttribs);
	    }
	    transmission.setRecipientArray(recipientArray);
	    
	    Map<String, Object> substitutionData = new HashMap<String, Object>();
	    substitutionData.put("name", student.getName());
	    substitutionData.put("host", host);
	    substitutionData.put("activationCode", student.getActivationCode());
	    
	    transmission.setSubstitutionData(substitutionData);

	    // Populate Email Body
	    TemplateContentAttributes contentAttributes = new TemplateContentAttributes();
	    contentAttributes.setFrom(new AddressAttributes(from,"Mozarella.tech",from));
	    contentAttributes.setSubject("Mozarella activation");
	    contentAttributes.setHtml("Dear {{name}}, thank you for your subscription"
	    		+"please visit the following link to activate your subsciption <a href=\"{{host}}/activate?activationCode={{activationCode}}\">{{host}}?activationCode={{activationCode}}</a>");
	    transmission.setContentAttributes(contentAttributes);

	    // Send the Email
	    client.setAuthKey(apiKey); 
	    //TODO why does this have to be done here?
	    
	    RestConnection connection = new RestConnection(client, apiEndpoint);
	    Response response = ResourceTransmissions.create(connection, 0, transmission);

	    LOGGER.info("Transmission Response: " + response);
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiEndpoint() {
		return apiEndpoint;
	}

	public void setApiEndpoint(String apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
	}
	
	

}