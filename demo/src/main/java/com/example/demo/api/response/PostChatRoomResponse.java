package com.example.demo.api.response;

import com.example.demo.api.entity.ChatRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class PostChatRoomResponse {
    private Long id;
    private String name;
    private String createdAt;
    private String updatedAt;
    public PostChatRoomResponse(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.name = chatRoom.getName();
        this.createdAt = chatRoom.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.updatedAt = chatRoom.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }
}
