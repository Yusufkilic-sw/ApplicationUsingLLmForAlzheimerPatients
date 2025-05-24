package com.example.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.Reminder;

public interface ReminderRepository extends MongoRepository<Reminder, String> {

	List<Reminder> findByUserId(String userId);

	void deleteByUserId(String userId);
}
