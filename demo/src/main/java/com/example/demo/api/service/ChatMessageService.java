package com.example.demo.api.service;

import com.example.demo.api.entity.ChatMessage;
import com.example.demo.api.repository.ChatMessageRepository;
import com.example.demo.api.request.PostChatMessageRequest;
import com.example.demo.api.response.PostChatMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

    private PostChatMessageResponse createChatMessage(PostChatMessageRequest postChatMessageRequest) {
        ChatMessage chatMessage = new ChatMessage(postChatMessageRequest);
        chatMessageRepository.save(chatMessage);
        return new PostChatMessageResponse(chatMessage);
    }
}
