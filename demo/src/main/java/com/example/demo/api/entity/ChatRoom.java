package com.example.demo.api.entity;

import com.example.demo.api.request.PostChatRoomRequest;
import com.example.demo.web.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.web.entity.BaseEntity.State.INACTIVE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages = new ArrayList<>();
    public ChatRoom(String name) {
        this.name = name;
    }
    public ChatRoom(PostChatRoomRequest postChatRoomRequest) {
        this.name = postChatRoomRequest.getName();
    }
    public void deleteChatRoom(String name) {
        this.state = INACTIVE;
    }
    public void addChatMessageToChatRoom(ChatMessage chatMessage) {
        chatMessages.add(chatMessage);
    }
}
