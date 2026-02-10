package com.java.chat.controller;


import com.java.chat.dto.ChatRequest;
import com.java.chat.dto.ChatResponse;
import com.java.chat.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@Valid @RequestBody ChatRequest req) {
        String out = chatService.chat(req.conversationId(), req.message());
        String[] parts = out.split("\\|\\|", 2);
        return new ChatResponse(parts[0], parts[1]);
    }
}
