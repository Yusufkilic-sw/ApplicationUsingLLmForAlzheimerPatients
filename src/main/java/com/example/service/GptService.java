package com.example.service;


import com.example.instructions.SystemPrompts;
import com.example.model.Prompt;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import okhttp3.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;



@Service
@RequiredArgsConstructor
public class GptService {

 
   @Value("${openai.api.key}")
    private String openaiApiKey;
    private final PromptService promptService;
    private  final ResponseManager responseManager;
   
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

   

    public String processPrompt(String userInput, String userId) throws IOException {
        String responseText = callGptApi(userInput, userId);

        Prompt prompt = new Prompt();
        prompt.setUserInput(userInput);
        prompt.setGptResponse(responseText);
        prompt.setUserId(userId);
        promptService.savePrompt(prompt);
        
        return responseManager.handleResponse(responseText, "1");
       
    }
        

    private String callGptApi(String userInput, String userId) throws IOException {
        MediaType mediaType = MediaType.get("application/json");
        List<Map<String, String>> recentMessages = promptService.getRecentConversationMessages(userId);
        
        if (recentMessages == null || recentMessages.isEmpty()) {
            recentMessages = new ArrayList<>(); 
            
        }
        
        List<Map<String, String>> messages = new ArrayList<>(SystemPrompts.getBaseInstructions());
    	messages.addAll(recentMessages);
    	messages.addAll(List.of(
        	    Map.of("role", "system", "content", "Aşağıdaki mesaj en güncel kullanıcı mesajıdır. Buna cevap ver."),
        	    Map.of("role", "user", "content", userInput)
        	));

    	Map<String, Object> payload = Map.of(
    	    "model", "gpt-4o",
    	    "messages", messages
    	);

        String bodyJson = objectMapper.writeValueAsString(payload);
        RequestBody body = RequestBody.create(bodyJson, mediaType);

        Request request = new Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .header("Authorization", "Bearer " + openaiApiKey)
            .header("Content-Type", "application/json")
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("GPT API failed: " + response.code() + " - " + response.message());
            }

            JsonNode json = objectMapper.readTree(response.body().string());
            return json.path("choices").path(0).path("message").path("content").asText().trim();
        }
    }

}
