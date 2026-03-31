package com.LungCancerDetection.Security.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;

    public String getRecommendation(String prompt) {

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}