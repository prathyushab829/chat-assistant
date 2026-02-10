package com.java.chat.service;

import com.java.chat.domain.ChatMessage;
import com.java.chat.domain.Conversation;
import com.java.chat.domain.MessageRole;
import com.java.llm.api.LlmClient;
import com.java.persistence.ConversationRepository;
import com.java.persistence.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ChatService {

    private final ConversationRepository conversationRepo;
    private final MessageRepository messageRepo;
    private final LlmClient llmClient;

    public ChatService(ConversationRepository conversationRepo, MessageRepository messageRepo, LlmClient llmClient) {
        this.conversationRepo = conversationRepo;
        this.messageRepo = messageRepo;
        this.llmClient = llmClient;
    }

    @Transactional
    public String chat(String conversationId, String userMessage) {
        String cid = conversationId;

        if (cid == null || cid.isBlank() || !conversationRepo.existsById(cid)) {
            Conversation c = conversationRepo.save(new Conversation());
            cid = c.getId();
        }

        messageRepo.save(new ChatMessage(cid, MessageRole.USER, userMessage));

        // fetch last messages (oldest → newest)
        List<ChatMessage> recentDesc = messageRepo.findTop20ByConversationIdOrderByCreatedAtDesc(cid);
        List<ChatMessage> recent = new ArrayList<>(recentDesc);
        Collections.reverse(recent);

        List<String> promptLines = recent.stream()
                .map(m -> m.getRole() + ": " + m.getContent())
                .toList();

        String reply = llmClient.generateReply(promptLines);

        messageRepo.save(new ChatMessage(cid, MessageRole.ASSISTANT, reply));

        return cid + "||" + reply; // quick return; controller will split
    }
}

