package com.example.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.example.model.TaskDefinition;

public interface TaskDefinitionRepository  extends MongoRepository<TaskDefinition, String>{

	void deleteAllByUserId(String userId);

	List<TaskDefinition> findByUserId(String userId);

}
