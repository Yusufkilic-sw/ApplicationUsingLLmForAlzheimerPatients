package com.example.repository;

import com.example.model.DrugIntakeLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DrugIntakeLogRepository extends MongoRepository<DrugIntakeLog, String> {
    boolean existsByUserIdAndDrugNameAndTakenAtBetween(String userId, String drugName, LocalDateTime start, LocalDateTime end);
    List<DrugIntakeLog> findByUserIdAndTakenAtBetween(String userId, LocalDateTime start, LocalDateTime end);
}