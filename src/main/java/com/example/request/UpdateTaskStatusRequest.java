package com.example.request;

import lombok.Data;

@Data
public class UpdateTaskStatusRequest {
    private String definitionId;
    private String userId;
    private String taskDescription;
    private String newStatus;
}