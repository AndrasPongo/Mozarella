package com.hybridtheory.mozarella.authorization.aspects;

import java.util.Optional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hybridtheory.mozarella.persistence.repository.LearnItemListRepository;
import com.hybridtheory.mozarella.users.Student;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;
import com.hybridtheory.mozzarella.authentication.JwtUtil;

@Component
@Aspect
public class LearnItemListAuthorizationAspect {

	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired 
	LearnItemListRepository learnItemListRepository; 
	
	private Boolean wasCalled = false;
	private static Logger LOGGER = LoggerFactory.getLogger(LearnItemListAuthorizationAspect.class);
	
    @Around("execution(* *Authorized(..))")
    public Object doAuthorization(ProceedingJoinPoint joinPoint) throws Throwable {
		LOGGER.info("entering authorized method"+ joinPoint.getStaticPart().getSignature().toString());
		Boolean isAuthorized = false;
		
		wasCalled = true;

		String authHeader = ((Optional<String>)(joinPoint.getArgs()[0])).get();
		String token = authHeader.substring(7);
		LOGGER.info("Auth header is :"+authHeader);
		
		Student studentTryingToAccessResource = jwtUtil.parseToken(token);
		LearnItemList learnItemListToModify;
		
		if(joinPoint.getSignature().getName().equals("addItemsAuthorized")){
			Integer listId = ((Integer)(joinPoint.getArgs()[1]));
			Integer supposedStudentId = learnItemListRepository.getOwnerOfListByListId(listId);
			
			isAuthorized = supposedStudentId == null || supposedStudentId.equals(studentTryingToAccessResource.getId());
		} else if(joinPoint.getSignature().getName().equals("saveLearnItemListAuthorized")){
			LearnItemList listToSave = (LearnItemList)(joinPoint.getArgs()[1]);
			if(listToSave.getId()==null){
				isAuthorized = true;
			} else {
				Integer supposedStudentId = learnItemListRepository.getOwnerOfListByListId(listToSave.getId());
				isAuthorized = studentTryingToAccessResource.getId().equals(supposedStudentId);
			}
			
		} else if(joinPoint.getSignature().getName().equals("getLearnItemListAuthorized") || joinPoint.getSignature().getName().equals("getItemsAuthorized")){
			//TODO implement this (not as important ad forbidding unauthorized modification)
			isAuthorized = true;
		}
    	
        Object result;
        
        if(isAuthorized){
        	result = joinPoint.proceed();
        } else {
        	result = null;
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
