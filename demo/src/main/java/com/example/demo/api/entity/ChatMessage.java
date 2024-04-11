package com.example.demo.api.entity;

import com.example.demo.api.request.PostChatMessageRequest;
import com.example.demo.web.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private String message;
    @ManyToOne
    private ChatRoom chatRoom;
    public ChatMessage(String sender,String message,ChatRoom chatRoom) {
        this.sender = sender;
        this.message = message;
        this.chatRoom = chatRoom;
    }
    public ChatMessage(PostChatMessageRequest postChatMessageRequest) {
        this.sender = postChatMessageRequest.getSender();
        this.message = postChatMessageRequest.getMessage();
        this.chatRoom = postChatMessageRequest.getChatRoom();
    }
}
