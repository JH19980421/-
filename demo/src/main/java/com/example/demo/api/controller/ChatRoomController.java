package com.example.demo.api.controller;

import com.example.demo.api.entity.Greeting;
import com.example.demo.api.entity.Message;
import com.example.demo.api.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

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
    @MessageMapping("/hello")
    @SendTo("/subscribe/greetings")
    public Greeting greeting(Message message) throws Exception {
        return new Greeting(HtmlUtils.htmlEscape(message.getMessage()));
    }
}
