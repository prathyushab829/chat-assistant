package com.java.llm.impl;


import com.java.llm.api.LlmClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MockLlmClient implements LlmClient {
    @Override
    public String generateReply(List<String> recentMessages) {
        String last = recentMessages.isEmpty() ? "" : recentMessages.get(recentMessages.size() - 1);
        return "Mock reply ✅ You said: " + last;
    }
}
