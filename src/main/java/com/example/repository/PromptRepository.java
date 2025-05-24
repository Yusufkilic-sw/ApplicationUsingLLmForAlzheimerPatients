package com.example.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Prompt;

@Repository
public interface PromptRepository extends MongoRepository<Prompt, String> {

	List<Prompt> findByUserIdAndTimestampAfter(String userId, Date fourDaysAgo);
	List<Prompt> findByUserId(String userId);
    List<Prompt> findByUserIdAndTimestampBetween(String userId, Date start, Date end);
	List<Prompt> findTop20ByUserIdOrderByTimestampDesc(String userId);
    
	
}

