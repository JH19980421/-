package com.example.demo.api.controller;

import com.example.demo.api.request.PostUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/app/users")
public class UserController {
    @GetMapping("/signup")
    public String createUser(Model model) {
        model.addAttribute("postUserRequest", new PostUserRequest());
        return "signin";
    }
}
