package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "health_logs")
public class HealthLog {

    @Id
    private String id;

    private String userId; // Reference to AppUser

    private LocalDateTime timestamp;

    private String type; // "activity", "mood", "symptom"

    private String description; // e.g., "Walked in the park", "Feeling anxious", "Headache"

    private String details; // Optional: more info, e.g., duration, intensity, etc.
}