package com.example.model;

import java.time.LocalDateTime;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "reminders")
@Data
public class Reminder {
    @Id
    private String id;

    private String userId;
    private String content;
    private LocalDateTime  date; 

  
}