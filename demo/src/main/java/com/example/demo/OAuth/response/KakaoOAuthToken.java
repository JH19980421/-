package com.example.demo.OAuth.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoOAuthToken {

    private String access_token;
    private String id_Token;
    private int expires_in;
    private String refresh_token;
    private String refresh_token_expires_in;
    private String token_type;
    private String scope;
}
