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

import com.hybridtheory.mozarella.api.StudentController;
import com.hybridtheory.mozarella.eventhandling.event.Event;
import com.hybridtheory.mozarella.eventhandling.result.NewResultAvailableEvent;
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

@Component
public class StudentRegisteredEventListener implements EventListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentRegisteredEventListener.class);
	
	@Value("${SPARKPOST_API_KEY}")
	String API_KEY;
	
	@Value("${SPARKPOST_API_URL}")
	String API_ENDPOINT;
	
    Client client = new Client(API_KEY);
    
    EventEmitter emitter;
    
	@Autowired
	StudentRegisteredEventListener(EventEmitter emitter){
		this.emitter = emitter;
		emitter.subscribe(this, StudentRegisteredEvent.class);
	}
	
	@Override
	public void beNotifiedOfEvent(Event e) {
		LOGGER.debug("api key: "+API_KEY);
		
		StudentRegisteredEvent studentRegisteredEvent = (StudentRegisteredEvent) e;
		
		List<String> recipients = new ArrayList<String>();
		//TODO: send email verification mail
		try {
			sendEmail("mozarella@mozarella.com", recipients, studentRegisteredEvent.getRegisteredStudent().getEmail());
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
	    RestConnection connection = new RestConnection(client, API_ENDPOINT);
	    LOGGER.debug("Api endpoint is: " + API_ENDPOINT);
	    LOGGER.debug("Api key is: " + API_KEY);
	    
	    Response response = ResourceTransmissions.create(connection, 0, transmission);

	    LOGGER.debug("Transmission Response: " + response);
	}

}