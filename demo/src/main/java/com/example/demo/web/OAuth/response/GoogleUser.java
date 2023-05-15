package com.example.demo.web.OAuth.response;

import com.example.demo.api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.demo.web.OAuth.SocialLoginType.GOOGLE;

@Getter
@NoArgsConstructor
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
        User user = new User(this.email,"NONE",this.name,GOOGLE);
        return user;
    }
}