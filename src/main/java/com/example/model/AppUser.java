package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class AppUser {
    
    @Id
    private String id;  // MongoDB generates this automatically

    private String name;
    private String surname;
    private String email;
    private String age;
    private String nameOfTheCaretaker;
    
}
