package com.hybridtheory.mozarella.authorization.aspects;

import static org.mockito.Mockito.when;

import java.util.List;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hybridtheory.mozarella.eventhandling.EventEmitter;
import com.hybridtheory.mozarella.persistence.LearnItemListRepositoryCustom;
import com.hybridtheory.mozarella.persistence.repository.LearnItemListRepository;
import com.hybridtheory.mozarella.persistence.repository.LearnItemRepository;
import com.hybridtheory.mozarella.persistence.repository.ResultRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentItemRecordRepository;
import com.hybridtheory.mozarella.persistence.repository.StudentRepository;
import com.hybridtheory.mozarella.users.StudentFactory;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;
import com.hybridtheory.mozarella.wordteacher.priority.LatestResultsBasedPriorityCalculator;
import com.hybridtheory.mozarella.wordteacher.priority.PriorityCalculator;
import com.hybridtheory.mozarella.wordteacher.teacher.ItemPrioritizer;
import com.hybridtheory.mozarella.wordteacher.teacher.ItemPrioritizerImpl1;
import com.hybridtheory.mozzarella.authentication.JwtUtil;

@Configuration
@ComponentScan(basePackages={"com.hybridtheory.mozarella.api","com.hybridtheory.mozarella.eventhandling"})
public class AspectTestConfig{

	@Bean
	public ResultRepository resultRepository(){
		return new ResultRepository(){
			public List<Result> getLastResultsForStudentAndLearnItem(@Param("studentId") Integer studentId, @Param("learnItemId") Integer learnItemId, Pageable pageable){
				return null;
			}

			@Override
			public void deleteAllInBatch() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void deleteInBatch(Iterable<Result> arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public List<Result> findAll() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<Result> findAll(Sort arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<Result> findAll(Iterable<Long> arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void flush() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Result getOne(Long arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <S extends Result> List<S> save(Iterable<S> arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <S extends Result> S saveAndFlush(S arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Page<Result> findAll(Pageable pageable) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <S extends Result> S save(S entity) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Result findOne(Long id) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean exists(Long id) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public long count() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void delete(Long id) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void delete(Result entity) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void delete(Iterable<? extends Result> entities) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void deleteAll() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	@Bean
	LearnItemListRepository learnItemListRepository(){
		LearnItemListRepository learnItemListRepository = Mockito.mock(LearnItemListRepository.class);
        
        return learnItemListRepository;
	}
	
	@Bean
	LearnItemListRepositoryCustom learnItemListRepositoryCustom(){
		LearnItemListRepositoryCustom learnItemListRepositoryCustom = Mockito.mock(LearnItemListRepositoryCustom.class);

        return learnItemListRepositoryCustom;
	}
	
	@Bean
	LearnItemRepository learnItemRepository(){
		LearnItemRepository learnItemRepository = Mockito.mock(LearnItemRepository.class);

        return learnItemRepository;
	}
	
	@Bean
	StudentRepository studentRepository(){
		StudentRepository studentRepository = Mockito.mock(StudentRepository.class);

        when(studentRepository.getOwnerOfListByListId(0)).thenReturn(0);
        when(studentRepository.getOwnerOfListByListId(1)).thenReturn(1);
        
        when(studentRepository.getOwnerOfItemByItemId(0)).thenReturn(0);
		
        return studentRepository;
	}
	
	@Bean
	StudentItemRecordRepository studentItemRecordRepository(){
		StudentItemRecordRepository studentItemRecordRepository = Mockito.mock(StudentItemRecordRepository.class);

        return studentItemRecordRepository;
	}
	
	@Bean
	StudentFactory studentFactory(){
		return new StudentFactory();
	}
	
	@Bean
	public ItemPrioritizer itemPrioritizer(){
		return new ItemPrioritizerImpl1();
	}
	
	@Bean
	protected PasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}
	  
	@Bean 
	JwtUtil jwtUtil(){
		JwtUtil jwtUtil = new JwtUtil();
		
		org.springframework.test.util.ReflectionTestUtils.setField(jwtUtil, "secret", "some secret");
		
		return jwtUtil;
	}
	
    @Bean
    public EventEmitter emitter(){
    	return new EventEmitter();
    }
	
    @Bean 
    PriorityCalculator priorityCalculator(){
    	return new LatestResultsBasedPriorityCalculator(3);
    }
}