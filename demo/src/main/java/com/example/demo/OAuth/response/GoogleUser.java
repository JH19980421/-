package com.example.demo.OAuth.response;

import com.example.demo.src.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.demo.OAuth.SocialLoginType.GOOGLE;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUser {
    public String id;
    public String email;
    public Boolean verifiedEmail;
    public String name;
    public String givenName;
    public String familyName;
    public String picture;
    public String locale;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password("NONE")
                .name(this.name)
                .isOAuth(GOOGLE)
                .build();
    }
}