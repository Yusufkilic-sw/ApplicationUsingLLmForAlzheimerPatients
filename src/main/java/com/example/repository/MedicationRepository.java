package com.example.repository;

import com.example.model.Medication;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MedicationRepository extends MongoRepository<Medication, String> {
    List<Medication> findByUserId(String userId);
}