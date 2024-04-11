package com.example.demo.api.service;

import com.example.demo.api.entity.ChatRoom;
import com.example.demo.api.repository.ChatRoomRepository;
import com.example.demo.api.request.PostChatRoomRequest;
import com.example.demo.api.response.PostChatRoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.demo.web.entity.BaseEntity.State.ACTIVE;

@RequiredArgsConstructor
@Service
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    private PostChatRoomResponse createChatRoom(PostChatRoomRequest postChatRoomRequest) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByNameAndState(postChatRoomRequest.getName(), ACTIVE);
        if (!chatRoom.isEmpty()) {
            return new PostChatRoomResponse(chatRoom.orElseThrow());
        }

        ChatRoom saveChatRoom = chatRoom.orElseThrow();
        chatRoomRepository.save(saveChatRoom);
        return new PostChatRoomResponse(saveChatRoom);
    }
}
