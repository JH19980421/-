package com.example.demo.api.service;

import com.example.demo.api.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;

//    private PostChatRoomResponse createChatMessage(PostChatMessageRequest postChatMessageRequest) {
//    }
}
