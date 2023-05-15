package com.example.demo.api.request;

import com.example.demo.web.OAuth.SocialLoginType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserRequest {
    private String email;
    private String password;
    private String name;
    private SocialLoginType isOAuth;

}
