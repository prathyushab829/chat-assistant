package com.java.chat.domain;


import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Conversation {
    @Id
    private String id = UUID.randomUUID().toString();

    private Instant createdAt = Instant.now();

    public String getId() { return id; }
    public Instant getCreatedAt() { return createdAt; }
}
