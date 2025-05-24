package com.example.controllers.gptcontroller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.request.GtpChatRequest;
import com.example.service.GptService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/gpt")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow all frontend origins (adjust as needed)
public class GptController {

    private final GptService gptService;

    

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody GtpChatRequest gtpChatRequest) {
        try {
            String gptResponse = gptService.processPrompt(gtpChatRequest.getUserInput(), gtpChatRequest.getUserId());
            return ResponseEntity.ok(gptResponse);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}