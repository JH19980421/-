package com.example.demo.src.request;

import com.example.demo.OAuth.SocialLoginType;
import com.example.demo.src.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.demo.OAuth.SocialLoginType.LOCAL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserRequest {
    private String email;
    private String password;
    private String name;
    private SocialLoginType isOAuth;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .isOAuth(LOCAL)
                .build();
    }
}
