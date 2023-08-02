package com.example.demo.api.controller;

import com.example.demo.api.request.PatchUserRequest;
import com.example.demo.api.request.PostLoginRequest;
import com.example.demo.api.request.PostUserRequest;
import com.example.demo.api.response.GetUserResponse;
import com.example.demo.api.response.PostLoginResponse;
import com.example.demo.api.response.PostUserResponse;
import com.example.demo.api.service.UserService;
import com.example.demo.web.OAuth.OAuthService;
import com.example.demo.web.OAuth.SocialLoginType;
import com.example.demo.web.OAuth.response.SocialOAuth;
import com.example.demo.web.exceptions.BaseException;
import com.example.demo.web.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.example.demo.api.utils.ValidationRegex.isRegexEmail;
import static com.example.demo.web.response.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/app/users")
public class UserController {
    private final UserService userService;
    private final OAuthService oAuthService;

    @PostMapping
    public BaseResponse<PostUserResponse> createUser(@RequestBody PostUserRequest postUserReq) {
        if (StringUtils.isBlank(postUserReq.getEmail())) {
            return new BaseResponse<>(USERS_EMPTY_EMAIL);
        }

        if (!isRegexEmail(postUserReq.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }

        PostUserResponse postUserRes = userService.createUser(postUserReq);
        return new BaseResponse<>(postUserRes);
    }

    @GetMapping
    public BaseResponse<List<GetUserResponse>> getUsers(@RequestParam(required = false) String email) {
        if (StringUtils.isBlank(email)) {
            return new BaseResponse<>(userService.getUsers());
        }
        // Get Users
        return new BaseResponse<>(userService.getUsersByEmail(email));
    }

    @GetMapping("/{userId}") // (GET) 127.0.0.1:9000/app/users/:userId
    public BaseResponse<GetUserResponse> getUser(@PathVariable("userId") Long userId) {
        GetUserResponse getUserRes = userService.getUser(userId);

        return new BaseResponse<>(getUserRes);
    }

    @PatchMapping("/{userId}")
    public BaseResponse<String> modifyUserName(@PathVariable("userId") Long userId, @RequestBody PatchUserRequest patchUserReq) {
        if (StringUtils.isBlank(patchUserReq.getName())) {
            return new BaseResponse<>(PATCH_USERS_NOT_ENTERED_NAME);
        }

        userService.modifyUserName(userId, patchUserReq);

        String result = "수정 완료!!";
        return new BaseResponse<>(result);

    }

    /**
     * 유저정보삭제 API
     * [DELETE] /app/users/:userId
     *
     * @return BaseResponse<String>
     */
    @DeleteMapping("/{userId}")
    public BaseResponse<String> deleteUser(@PathVariable("userId") Long userId) {
//        Long jwtUserId = jwtService.getUserId();

        userService.deleteUser(userId);

        String result = "삭제 완료!!";
        return new BaseResponse<>(result);
    }

    /**
     * 로그인 API
     * [POST] /app/users/logIn
     *
     * @return BaseResponse<PostLoginRes>
     */
    @PostMapping("/logIn")
    public BaseResponse<PostLoginResponse> logIn(@RequestBody PostLoginRequest postLoginReq) {
        // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
        // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
        PostLoginResponse postLoginRes = userService.logIn(postLoginReq);
        return new BaseResponse<>(postLoginRes);
    }


    /**
     * 유저 소셜 가입, 로그인 인증으로 리다이렉트 해주는 url
     * [GET] /app/users/auth/:socialLoginType/login
     *
     * @return void
     */
    @GetMapping("/auth/{socialLoginType}/login")
    public void socialLoginRedirect(@PathVariable(name = "socialLoginType") String SocialLoginPath) throws IOException {
        SocialLoginType socialLoginType = SocialLoginType.valueOf(SocialLoginPath.toUpperCase());
        oAuthService.accessRequest(socialLoginType);
    }


    /**
     * Social Login API Server 요청에 의한 callback 을 처리
     *
     * @param socialLoginPath (GOOGLE, NAVER, KAKAO)
     * @param code            API Server 로부터 넘어오는 code
     * @return SNS Login 요청 결과로 받은 Json 형태의 java 객체 (access_token, jwt_token, user_num 등)
     */
    @ResponseBody
    @GetMapping("/auth/{socialLoginType}/login/callback")
    public BaseResponse<SocialOAuth> socialLoginCallback(
            @PathVariable(name = "socialLoginType") String socialLoginPath,
            @RequestParam(name = "code") String code) throws IOException, BaseException {
        log.info(">> 소셜 로그인 API 서버로부터 받은 code : {}", code);
        SocialLoginType socialLoginType = SocialLoginType.valueOf(socialLoginPath.toUpperCase());
        SocialOAuth getSocialOAuthRes = oAuthService.oAuthLoginOrJoin(socialLoginType, code);
        return new BaseResponse<>(getSocialOAuthRes);
    }
}