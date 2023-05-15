package com.example.demo.web.OAuth.response;

import com.example.demo.api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.demo.web.OAuth.SocialLoginType.KAKAO;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUser {
    private String nickname;
    private String email;

    public User toEntity() {
        User user = new User(this.email,"NONE",this.nickname, KAKAO);
        return user;
    }
}
