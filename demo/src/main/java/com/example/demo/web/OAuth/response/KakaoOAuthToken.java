package com.example.demo.web.OAuth.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoOAuthToken {

    private String accessToken;
    private String idToken;
    private int expiresIn;
    private String refreshToken;
    private String refreshTokenExpiresIn;
    private String tokenType;
    private String scope;
}
