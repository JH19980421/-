package com.example.demo.api.controller;
import com.example.demo.api.entity.MemberDetails;
import com.example.demo.api.request.PatchUserRequest;
import com.example.demo.api.request.PostUserRequest;
import com.example.demo.api.response.GetUserResponse;
import com.example.demo.api.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("")
public class UserController {
    private final UserService userService;
    @GetMapping("/sign")
    public String signInUp() {
        return "sign";
    }

    @GetMapping("/profile")
    public String getProfileofUser(Model model, @AuthenticationPrincipal MemberDetails memberDetails) {
        GetUserResponse getUserResponse = userService.getUserByEmail(memberDetails.getUsername());
        model.addAttribute("user", getUserResponse);
        return "profile";
    }

    @PostMapping("/profile/modify")
    public String modifyUserForm(PostUserRequest user, BindingResult bindingResult, @AuthenticationPrincipal MemberDetails memberDetails) {
        if (bindingResult.hasErrors()){
            return "redirect:home";
        }

        userService.modifyAccountDetail(memberDetails.getUsername(), user);

        return "redirect:/app/boards/home";
    }
}
