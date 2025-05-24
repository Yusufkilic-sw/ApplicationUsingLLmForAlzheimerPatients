package com.example.core.extracontrollers;

import com.example.model.Reminder;
import com.example.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReminderController {

    private final ReminderService reminderService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reminder>> getRemindersByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(reminderService.getRemindersByUserId(userId));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteAllRemindersByUserId(@PathVariable String userId) {
        reminderService.deleteAllRemindersByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{reminderId}/user/{userId}")
    public ResponseEntity<Void> deleteReminderByIdAndUserId(@PathVariable String reminderId, @PathVariable String userId) {
        reminderService.deleteReminderByIdAndUserId(reminderId, userId);
        return ResponseEntity.noContent().build();
    }
}