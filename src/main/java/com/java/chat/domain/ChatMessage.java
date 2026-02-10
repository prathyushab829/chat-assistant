package com.java.chat.domain;


import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class ChatMessage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String conversationId;

    @Enumerated(EnumType.STRING)
    private MessageRole role;

    @Column(length = 8000)
    private String content;

    private Instant createdAt = Instant.now();

    protected ChatMessage() {}

    public ChatMessage(String conversationId, MessageRole role, String content) {
        this.conversationId = conversationId;
        this.role = role;
        this.content = content;
    }

    public Long getId() { return id; }
    public String getConversationId() { return conversationId; }
    public MessageRole getRole() { return role; }
    public String getContent() { return content; }
    public Instant getCreatedAt() { return createdAt; }
}

