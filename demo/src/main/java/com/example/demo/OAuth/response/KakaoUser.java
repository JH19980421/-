package com.example.demo.OAuth.response;

import com.example.demo.src.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.demo.OAuth.SocialLoginType.KAKAO;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUser {
    private String nickname;
    private String email;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password("NONE")
                .name(this.nickname)
                .isOAuth(KAKAO)
                .build();
    }
}
