package com.hybridtheory.mozarella.authorization.aspects;

import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hybridtheory.mozarella.persistence.repository.StudentRepository;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;
import com.hybridtheory.mozzarella.authentication.JwtUtil;

@Component
@Aspect
public class LearnItemAuthorizationAspect {
    
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired 
	StudentRepository studentRepository; 
	
	private Boolean wasCalled = false;
	private static Logger LOGGER = LoggerFactory.getLogger(LearnItemListAuthorizationAspect.class);
	
	
	@Around("execution(* com.hybridtheory.mozarella.api.LearnItemController.*Authorized(..))")
    public Object doAuthorization(ProceedingJoinPoint joinPoint) throws Throwable {
		LOGGER.info("entering authorized method"+ joinPoint.getStaticPart().getSignature().toString());
		Boolean isAuthorized = false;
		wasCalled = true;

		String authHeader = ((Optional<String>)(joinPoint.getArgs()[0])).get();
		String token = authHeader.substring(7);
		LOGGER.info("Auth header is :"+authHeader);
		
		Student studentTryingToAccessResource = jwtUtil.parseToken(token);
		
		if(joinPoint.getSignature().getName().equals("deleteItemAuthorized")){
			Integer itemId = ((Integer)(joinPoint.getArgs()[1]));
			Integer supposedStudentId = studentRepository.getOwnerOfItemByItemId(itemId);
			
			isAuthorized = supposedStudentId.equals(studentTryingToAccessResource.getId());
		}
    	
        Object result = null;
        
        if(isAuthorized){
        	result = joinPoint.proceed();
        }
        
        return result;
    }

	public Boolean getWasCalled() {
		return wasCalled;
	}

	public void setWasCalled(boolean b) {
		this.wasCalled = false;
	}
}
