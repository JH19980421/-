package com.example.demo.api.controller;

import com.example.demo.api.entity.Greeting;
import com.example.demo.api.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatMessageController {
    @MessageMapping("/hello")
    @SendTo("/subscribe/greetings")
    public Greeting greeting(Message message) throws Exception {
        return new Greeting(HtmlUtils.htmlEscape(message.getMessage()));
    }
}
