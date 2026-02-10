package com.java.llm.api;


import java.util.List;

public interface LlmClient {
    String generateReply(List<String> recentMessages);
}

