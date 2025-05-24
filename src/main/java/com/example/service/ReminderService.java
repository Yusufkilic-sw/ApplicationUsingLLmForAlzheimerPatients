package com.example.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.example.model.Reminder;
import com.example.repository.ReminderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository reminderRepository;

    

    public List<Reminder> getRemindersByUserId(String userId) {
        return reminderRepository.findByUserId(userId);
    }

    public void deleteAllRemindersByUserId(String userId) {
        reminderRepository.deleteByUserId(userId);
    }

    public void deleteReminderByIdAndUserId(String reminderId, String userId) {
        Reminder reminder = reminderRepository.findById(reminderId).orElse(null);
        if (reminder != null && reminder.getUserId().equals(userId)) {
            reminderRepository.deleteById(reminderId);
        }
    }
}
