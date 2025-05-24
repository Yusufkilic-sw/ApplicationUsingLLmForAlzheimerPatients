package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.dtos.DrugDto;


import java.util.List;

@Data
@Document(collection = "medications")
public class Medication {

    @Id
    private String id;

    private String userId; // Reference to AppUser

    private List<DrugDto> drugs; // List of drugs for this medication schedule

   

    private String notes; // Any additional notes
}