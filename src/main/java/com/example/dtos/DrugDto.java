package com.example.dtos;

import lombok.Data;
import java.time.LocalTime;

@Data
public class DrugDto {
    private String name;
    private String dosage;
    private LocalTime timeOfDay; // When to take (scheduled)
    private String instructions;
}