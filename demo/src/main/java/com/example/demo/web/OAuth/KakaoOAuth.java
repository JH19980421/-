package com.example.demo.web.OAuth;

import com.example.demo.web.OAuth.response.KakaoOAuthToken;
import com.example.demo.web.OAuth.response.KakaoUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoOAuth implements SocialOAuth {

    @Value("${spring.OAuth2.kakao.url}")
    private String KAKAO_SNS_URL;
    @Value("${spring.OAuth2.kakao.client-id}")
    private String KAKAO_SNS_CLIENT_ID;
    @Value("${spring.OAuth2.kakao.callback-login-url}")
    private String KAKAO_SNS_CALLBACK_LOGIN_URL;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Override
    public String getOauthRedirectURL() {

        Map<String, Object> params = new HashMap<>();
        params.put("response_type", "code");
        params.put("client_id", KAKAO_SNS_CLIENT_ID);
        params.put("redirect_uri", KAKAO_SNS_CALLBACK_LOGIN_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));
        String redirectURL = KAKAO_SNS_URL + "?" + parameterString;
        log.info("redirectURL = " + redirectURL);

        return redirectURL;
    }

    public ResponseEntity<String> requestAccessToken(String code) {
        String KAKAO_TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> accessTokenParams = new LinkedMultiValueMap<>();
        accessTokenParams.add("code", code);
        accessTokenParams.add("client_id", KAKAO_SNS_CLIENT_ID);
        accessTokenParams.add("redirect_uri", KAKAO_SNS_CALLBACK_LOGIN_URL);
        accessTokenParams.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(accessTokenParams, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                KAKAO_TOKEN_REQUEST_URL,
                HttpMethod.POST,
                accessTokenRequest,
                String.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            return responseEntity;
        }

        return null;
    }

    public KakaoOAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        log.info("response.getBody() = {}", response.getBody());

        KakaoOAuthToken kakaoOAuthToken= objectMapper.readValue(response.getBody(), KakaoOAuthToken.class);
        System.out.println("AccessToken: " + kakaoOAuthToken.getAccessToken());

        return kakaoOAuthToken;
    }

    public ResponseEntity<String> requestUserInfo(KakaoOAuthToken oAuthToken) {
        String KAKAO_USERINFO_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer " + oAuthToken.getAccessToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(KAKAO_USERINFO_REQUEST_URL, HttpMethod.GET,request,String.class);

        log.info("response.getBody() = {}", response.getBody());

        return response;
    }

    public KakaoUser getUserInfo(ResponseEntity<String> userInfoRes) throws JsonProcessingException{
        String str = userInfoRes.getBody().toString();

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(str);

        String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
        String email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();

        KakaoUser kakaoUser = new KakaoUser(nickname, email);

        return kakaoUser;
    }
}
