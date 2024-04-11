package com.example.demo.api.request;

import com.example.demo.api.entity.ChatRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostChatMessageRequest {
    private String sender;
    private String message;
    private ChatRoom chatRoom;
}
