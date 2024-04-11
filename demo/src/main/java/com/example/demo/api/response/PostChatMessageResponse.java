package com.example.demo.api.response;

import com.example.demo.api.entity.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class PostChatMessageResponse {
    private String sender;
    private String message;
    private String createdAt;
    private String updatedAt;
    public PostChatMessageResponse(ChatMessage chatMessage) {
        this.sender = chatMessage.getSender();
        this.message = chatMessage.getMessage();
        this.createdAt = chatMessage.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
        this.updatedAt = chatMessage.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }
}
