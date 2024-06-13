package com.example.demo.api.controller;

import com.example.demo.api.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/app/chats")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("")
    public String chatRoom() {
        return "chat";
    }
}
