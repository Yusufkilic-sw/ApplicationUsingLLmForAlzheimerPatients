package com.example.model;

import com.example.dtos.TaskDto;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "task_definitions")
public class TaskDefinition {

    @Id
    private String id;

    private String userId;

    private List<TaskDto> tasks;

    private boolean completed;
}
