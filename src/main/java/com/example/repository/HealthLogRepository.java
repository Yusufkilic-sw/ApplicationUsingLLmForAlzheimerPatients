package com.example.repository;

import com.example.model.HealthLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HealthLogRepository extends MongoRepository<HealthLog, String> {
    List<HealthLog> findByUserId(String userId);
}