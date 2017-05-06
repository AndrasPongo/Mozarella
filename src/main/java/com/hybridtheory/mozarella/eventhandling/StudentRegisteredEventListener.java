package com.hybridtheory.mozarella.eventhandling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hybridtheory.mozarella.eventhandling.event.Event;
import com.hybridtheory.mozarella.eventhandling.result.StudentRegisteredEvent;
import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;
import com.sparkpost.model.AddressAttributes;
import com.sparkpost.model.RecipientAttributes;
import com.sparkpost.model.TemplateContentAttributes;
import com.sparkpost.model.TransmissionWithRecipientArray;
import com.sparkpost.model.responses.Response;
import com.sparkpost.resources.ResourceTransmissions;
import com.sparkpost.transport.RestConnection;

public class StudentRegisteredEventListener implements EventListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentRegisteredEventListener.class);
	
	@Value("${sparkpost.api.key}")
	private String apiKey;
	
	@Value("${sparkpost.api.url}")
	private String apiEndpoint;
	
    private Client client = new Client(apiKey);
    
    
    private EventEmitter emitter;
    
    @Autowired
	public StudentRegisteredEventListener(EventEmitter emitter){
		this.emitter = emitter;
		emitter.subscribe(this, StudentRegisteredEvent.class);
	}
	
	@Override
	public void beNotifiedOfEvent(Event e) {
		LOGGER.debug("api key: "+apiKey);
		
		StudentRegisteredEvent studentRegisteredEvent = (StudentRegisteredEvent) e;
		
		List<String> recipients = new ArrayList<String>();
		//TODO: send email verification mail
		try {
			sendEmail("mail.mozarella.tech", recipients, studentRegisteredEvent.getRegisteredStudent().getEmail());
		} catch (SparkPostException e1) {
			e1.printStackTrace();
			LOGGER.error(e1.getMessage());
		}
	}
	
	private void sendEmail(String from, List<String> recipients, String email) throws SparkPostException {
	    TransmissionWithRecipientArray transmission = new TransmissionWithRecipientArray();

	    // Populate Recipients
	    List<RecipientAttributes> recipientArray = new ArrayList<RecipientAttributes>();
	    for (String recipient : recipients) {
	    RecipientAttributes recipientAttribs = new RecipientAttributes();
	        recipientAttribs.setAddress(new AddressAttributes(recipient));
	        recipientArray.add(recipientAttribs);
	    }
	    transmission.setRecipientArray(recipientArray);

	     // Populate Substitution Data
	    Map<String, Object> substitutionData = new HashMap<String, Object>();
	    substitutionData.put("yourContent", "You can add substitution data too.");
	    transmission.setSubstitutionData(substitutionData);

	    // Populate Email Body
	    TemplateContentAttributes contentAttributes = new TemplateContentAttributes();
	    contentAttributes.setFrom(new AddressAttributes(from));
	    contentAttributes.setSubject("Your subject content here. {{yourContent}}");
	    contentAttributes.setText("Your Text content here.  {{yourContent}}");
	    contentAttributes.setHtml("<p>Your <b>HTML</b> content here.  {{yourContent}}</p>");
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