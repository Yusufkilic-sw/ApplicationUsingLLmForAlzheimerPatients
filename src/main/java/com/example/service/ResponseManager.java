package com.example.service;

import com.example.dtos.TaskDto;

import com.example.model.Reminder;
import com.example.model.TaskDefinition;

import com.example.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class ResponseManager {

    private final ReminderRepository reminderRepository;
    private final TaskDefinitionService taskDefinitionService;
    
    public String handleResponse(String gptResponse, String userId) {
        String[] lines = gptResponse.split("\\R", 2); 
        String firstLine = lines.length > 0 ? lines[0].trim() : "";

        if (firstLine.startsWith("[Reminder]")) {
            return handleReminderResponse(gptResponse, userId);
        }
        if (firstLine.startsWith("[DailyTask]")) {
            return handleDailyTaskResponses(gptResponse, userId);
        }
        
        

        else {

        	return gptResponse;
        }
    }


    public String handleReminderResponse(String gptResponse, String userId) {
    	
        String hatirlatma = extractSection(gptResponse, "Response");
        String gorev = extractSection(gptResponse, "Task");
        String zamanStr = extractSection(gptResponse, "Time");

        if (gorev != null && zamanStr != null) {
        	LocalDateTime zaman = calculateDateTime(zamanStr);
            saveReminder(userId, gorev, zaman);
        }

        return hatirlatma != null ? hatirlatma : gptResponse;
    }
    

    private String extractSection(String text, String sectionName) {
        Pattern pattern = Pattern.compile("\\[" + sectionName + "\\]:\\s*(.+)");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : null;
    }

    public LocalDateTime calculateDateTime(String timeString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime parsedTime = LocalTime.parse(timeString.trim(), formatter);

            LocalDate today = LocalDate.now();
            LocalDateTime candidateDateTime = LocalDateTime.of(today, parsedTime);

            if (candidateDateTime.isBefore(LocalDateTime.now())) {
                candidateDateTime = candidateDateTime.plusDays(1);
            }

            return candidateDateTime.plusHours(3); // Always add 3 hours
        } catch (Exception e) {
            return LocalDateTime.now().plusHours(3); // fallback with +3 hours
        }
    }



    private void saveReminder(String userId, String content, LocalDateTime dateTime) {
        Reminder reminder = new Reminder();
        reminder.setUserId(userId);
        reminder.setContent(content);
        reminder.setDate(dateTime);
        reminderRepository.save(reminder);
    }
    
    public String handleDailyTaskResponses(String gptResponse, String userId) {
        if (gptResponse == null || !gptResponse.contains("[DailyTask]")) return null;

        List<TaskDto> taskDtoList = new ArrayList<>();
        String responseLine = null;

        // Extract the [Response] and [Tasks] sections
        String[] lines = gptResponse.split("\\R");
        boolean inTasksSection = false;

        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("[Response]:")) {
                // Capture the response line
                responseLine = line.substring("[Response]:".length()).trim();
            }

            if (line.startsWith("[Tasks]")) {
                inTasksSection = true;
                continue;
            }

            if (inTasksSection) {
                if (line.matches("^\\d+\\.\\s+.+")) {
                    String taskText = line.substring(line.indexOf('.') + 1).trim();
                    TaskDto taskDto = new TaskDto();
                    taskDto.setTask(taskText);
                    taskDto.setStatus("pending");
                    taskDtoList.add(taskDto);
                }
            }
        }

        if (!taskDtoList.isEmpty()) {
            TaskDefinition definition = new TaskDefinition();
            definition.setUserId(userId);
            definition.setTasks(taskDtoList);
            definition.setCompleted(false);
            taskDefinitionService.save(definition);
        }

        return responseLine;
    }

   

    
    
}
