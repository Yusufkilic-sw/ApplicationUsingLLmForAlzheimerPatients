package com.example.service;

import com.example.dtos.TaskDto;
import com.example.model.HealthLog;
import com.example.model.TaskDefinition;
import com.example.repository.TaskDefinitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskDefinitionService {

    private final TaskDefinitionRepository repository;
    private final HealthLogService healthLogService;

    public TaskDefinition save(TaskDefinition taskDefinition) {
        return repository.save(taskDefinition);
    }

    public Optional<TaskDefinition> findById(String id) {
        return repository.findById(id);
    }

    public List<TaskDefinition> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public void deleteByUserId(String userId) {
        repository.deleteAllByUserId(userId);
    }

    public TaskDefinition updateStatus(String id, boolean completed) {
        Optional<TaskDefinition> optional = repository.findById(id);
        if (optional.isPresent()) {
            TaskDefinition task = optional.get();
            task.setCompleted(completed);
            return repository.save(task);
        }
        throw new RuntimeException("TaskDefinition not found with id: " + id);
    }
    
    public TaskDefinition updateTaskStatus(String definitionId,String userId, String taskDescription, String newStatus) {
        Optional<TaskDefinition> optional = repository.findById(definitionId);
        if (optional.isPresent()) {
            TaskDefinition definition = optional.get();
            List<TaskDto> tasks = definition.getTasks();

            boolean updated = false;
            for (TaskDto task : tasks) {
                if (task.getTask().equalsIgnoreCase(taskDescription)) {
                    task.setStatus(newStatus);
                    updated = true;
                    break;
                }
            }

            if (!updated) {
                throw new RuntimeException("Task not found: " + taskDescription);
            }
            HealthLog log = new HealthLog();
            log.setUserId(userId);
            log.setTimestamp(LocalDateTime.now());
            log.setType("task");
            log.setDescription("Task completed: " );
            log.setDetails("Task details: " + taskDescription);
            healthLogService.saveHealthLog(log);

            return repository.save(definition);

        }

        throw new RuntimeException("TaskDefinition not found with id: " + definitionId);
    }

}
