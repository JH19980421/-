package com.example.demo.api.controller;
import com.example.demo.api.request.PostLoginRequest;
import com.example.demo.api.request.PostUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/app/users")
public class UserController {
    @GetMapping("/signin")
    public String loginUser(Model model) {
        model.addAttribute("postLoginRequest", new PostLoginRequest());
        return "signinup";
    }
    @GetMapping("/signup")
    public String createUser(Model model) {
        model.addAttribute("postUserRequest", new PostUserRequest());
        return "signinup";
    }
}
