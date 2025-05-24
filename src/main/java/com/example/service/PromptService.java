package com.example.service;

import com.example.model.Prompt;
import com.example.repository.PromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PromptService {

    private final PromptRepository promptRepository;

    // Save a prompt
    public Prompt savePrompt(Prompt prompt) {
        if (prompt.getTimestamp() == null) {
            prompt.setTimestamp(new Date());
        }
        return promptRepository.save(prompt);
    }
    
    public List<Map<String, String>> getRecentConversationMessages(String userId) {
        // Fetch the last 20 prompts ordered by timestamp descending
        List<Prompt> prompts = promptRepository.findTop20ByUserIdOrderByTimestampDesc(userId);

        // If no prompts are found, return a system message
        if (prompts == null || prompts.isEmpty()) {
            return List.of(
                Map.of("role", "system", "content", "Son 4 güne ait geçmiş mesaj bulunamadı.")
            );
        }

        // Reverse to show in chronological order (oldest first)
        Collections.reverse(prompts);

        // Flatten into OpenAI message format
        return prompts.stream()
            .flatMap(p -> Stream.of(
                Map.of("role", "user", "content", p.getUserInput()),
                Map.of("role", "assistant", "content", p.getGptResponse())
            ))
            .collect(Collectors.toList());
    }

    // Get all prompts for a user
    public List<Prompt> getPromptsByUser(String userId) {
        return promptRepository.findByUserId(userId);
    }

    // Get prompts within a given date range
    public List<Prompt> getPromptsByUserInPeriod(String userId, Date startDate, Date endDate) {
        return promptRepository.findByUserIdAndTimestampBetween(userId, startDate, endDate);
    }

    // Get prompts after a given date (e.g. last 4 days)
    public List<Prompt> getPromptsByUserAfter(String userId, Date afterDate) {
        return promptRepository.findByUserIdAndTimestampAfter(userId, afterDate);
    }
}
