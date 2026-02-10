package com.java.persistence;


import com.java.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findTop20ByConversationIdOrderByCreatedAtDesc(String conversationId);
}

