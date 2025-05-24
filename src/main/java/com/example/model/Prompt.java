package com.example.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "prompts")
@Data
public class Prompt {
    @Id
    private String id;
    private String userId;
    private String userInput;
    private String gptResponse;
    private Date timestamp = new Date();

    // Getters/setters or use Lombok
}

