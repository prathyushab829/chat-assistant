package com.java.llm.impl;

import com.java.llm.api.LlmClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class OpenAiLlmClient implements LlmClient {

    private final WebClient webClient;
    private final String model;
    private final boolean enabled;

    public OpenAiLlmClient(
            @Value("${chat.llm.openai.base-url}") String baseUrl,
            @Value("${chat.llm.openai.api-key:}") String apiKey,
            @Value("${chat.llm.openai.model}") String model,
            @Value("${chat.llm.provider}") String provider
    ) {
        this.model = model;
        this.enabled = "openai".equalsIgnoreCase(provider) && apiKey != null && !apiKey.isBlank();
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public String generateReply(List<String> recentMessages) {
        if (!enabled) {
            throw new IllegalStateException("OpenAI client not enabled. Set chat.llm.provider=openai and OPENAI_API_KEY.");
        }

        // For interviews: keep it simple. You can replace with the exact OpenAI Responses API schema later.
        Map<String, Object> payload = Map.of(
                "model", model,
                "input", String.join("\n", recentMessages)
        );

        return webClient.post()
                .uri("/v1/responses")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .map(m -> String.valueOf(m.getOrDefault("output_text", "")))
                .block();
    }
}

