package com.example.demo.api.service;

import com.example.demo.web.exceptions.BaseException;
import com.example.demo.api.entity.User;
import com.example.demo.api.repository.UserRepository;
import com.example.demo.api.request.PatchUserRequest;
import com.example.demo.api.request.PostLoginRequest;
import com.example.demo.api.request.PostUserRequest;
import com.example.demo.api.response.GetUserResponse;
import com.example.demo.api.response.PostLoginResponse;
import com.example.demo.api.response.PostUserResponse;
import com.example.demo.api.utils.JwtService;
import com.example.demo.api.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.web.entity.BaseEntity.State.ACTIVE;
import static com.example.demo.web.response.BaseResponseStatus.*;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    //POST
    public PostUserResponse createUser(PostUserRequest postUserRequest) {
        //중복 체크
        userRepository.findByEmailAndState(postUserRequest.getEmail(), ACTIVE)
                .orElseThrow(() -> new BaseException(POST_USERS_EXISTS_EMAIL));

        String encryptPwd = new SHA256().encrypt(postUserRequest.getPassword());

        User saveUser = User.createUser(postUserRequest.getEmail(), encryptPwd, postUserRequest.getName(), postUserRequest.getIsOAuth());
        userRepository.save(saveUser);
        return new PostUserResponse(saveUser.getId());

    }

    public PostUserResponse createOAuthUser(User user) {
        System.out.println(user.getEmail());
        System.out.println(user.getName());
        User saveUser = userRepository.save(user);

        // JWT 발급
        String jwtToken = jwtService.createJwt(saveUser.getId());
        return new PostUserResponse(saveUser.getId(), jwtToken);

    }

    public void modifyUserName(Long userId, PatchUserRequest patchUserRequest) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        user.updateName(patchUserRequest.getName());
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        user.inActive();
    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> getUsers() {
        return userRepository.findAllByState(ACTIVE).stream()
                .map(GetUserResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> getUsersByEmail(String email) {
        return userRepository.findAllByEmailAndState(email, ACTIVE).stream()
                .map(GetUserResponse::new)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public GetUserResponse getUser(Long userId) {
        return new GetUserResponse(
                userRepository.findByIdAndState(userId, ACTIVE)
                        .orElseThrow(() -> new BaseException(NOT_FIND_USER))
        );
    }

    @Transactional(readOnly = true)
    public boolean checkUserByEmail(String email) {
        if (Objects.nonNull(userRepository.findByEmailAndState(email, ACTIVE))) return true;

        return false;
    }

    public PostLoginResponse logIn(PostLoginRequest postLoginRequest) {
        User user = userRepository.findByEmailAndState(postLoginRequest.getEmail(), ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        if (isNotMatchedPassword(postLoginRequest.getPassword(), user.getPassword())) {
            throw new BaseException(FAILED_TO_LOGIN);
        }

        return new PostLoginResponse(
                user.getId(),
                jwtService.createJwt(user.getId())
        );
    }

    private boolean isNotMatchedPassword(String plainPassword, String encryptPassword) {

        String encrypt = new SHA256().encrypt(plainPassword);
        return !encrypt.equals(encryptPassword);
    }

    public GetUserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmailAndState(email, ACTIVE).orElseThrow(() -> new BaseException(NOT_FIND_USER));
        return new GetUserResponse(user);
    }
}
