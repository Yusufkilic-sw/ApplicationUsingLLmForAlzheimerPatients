package com.example.core.extracontrollers;

import com.example.model.TaskDefinition;
import com.example.request.UpdateTaskStatusRequest;
import com.example.service.TaskDefinitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/task-definitions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TaskDefinitionController {

    private final TaskDefinitionService taskDefinitionService;

    @PostMapping("/create")
    public ResponseEntity<TaskDefinition> createTaskDefinition(@RequestBody TaskDefinition taskDefinition) {
        TaskDefinition saved = taskDefinitionService.save(taskDefinition);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDefinition> getTaskDefinitionById(@PathVariable String id) {
        Optional<TaskDefinition> task = taskDefinitionService.findById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDefinition>> getTaskDefinitionsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(taskDefinitionService.findByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskDefinitionById(@PathVariable String id) {
        taskDefinitionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteTaskDefinitionsByUserId(@PathVariable String userId) {
        taskDefinitionService.deleteByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TaskDefinition> updateTaskDefinitionStatus(@PathVariable String id, @RequestParam boolean completed) {
        TaskDefinition updated = taskDefinitionService.updateStatus(id, completed);
        return ResponseEntity.ok(updated);
    }

   @PutMapping("/task-status")
    public ResponseEntity<TaskDefinition> updateTaskStatus(
            @RequestBody UpdateTaskStatusRequest request) {
        TaskDefinition updated = taskDefinitionService.updateTaskStatus(
                request.getDefinitionId(),
                request.getUserId(),
                request.getTaskDescription(),
                request.getNewStatus()
        );
        return ResponseEntity.ok(updated);
    }
}