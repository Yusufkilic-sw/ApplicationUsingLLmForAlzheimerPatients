package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "drug_intake_logs")
public class DrugIntakeLog {
    @Id
    private String id;
    private String userId;
    private String medicationId;
    private String drugName;
    private LocalDateTime takenAt;
}