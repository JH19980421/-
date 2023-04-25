package com.example.demo.src.service;

import com.example.demo.exceptions.BaseException;
import com.example.demo.src.entity.User;
import com.example.demo.src.repository.UserRepository;
import com.example.demo.src.request.PatchUserRequest;
import com.example.demo.src.request.PostLoginRequest;
import com.example.demo.src.request.PostUserRequest;
import com.example.demo.src.response.GetUserResponse;
import com.example.demo.src.response.PostLoginResponse;
import com.example.demo.src.response.PostUserResponse;
import com.example.demo.src.utils.JwtService;
import com.example.demo.src.utils.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.entity.BaseEntity.State.ACTIVE;
import static com.example.demo.response.BaseResponseStatus.*;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    //POST
    public PostUserResponse createUser(PostUserRequest postUserRequest) {
        //중복 체크
        Optional<User> checkUser = userRepository.findByEmailAndState(postUserRequest.getEmail(), ACTIVE);
        if(checkUser.isPresent() == true){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        String encryptPwd;

        try {
            encryptPwd = new SHA256().encrypt(postUserRequest.getPassword());
            postUserRequest.setPassword(encryptPwd);
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        User saveUser = userRepository.save(postUserRequest.toEntity());
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
        user.deleteUser();
    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> getUsers() {
        List<GetUserResponse> getUserResList = userRepository.findAllByState(ACTIVE).stream()
                .map(GetUserResponse::new)
                .collect(Collectors.toList());
        return getUserResList;
    }

    @Transactional(readOnly = true)
    public List<GetUserResponse> getUsersByEmail(String email) {
        List<GetUserResponse> getUserResList = userRepository.findAllByEmailAndState(email, ACTIVE).stream()
                .map(GetUserResponse::new)
                .collect(Collectors.toList());
        return getUserResList;
    }


    @Transactional(readOnly = true)
    public GetUserResponse getUser(Long userId) {
        User user = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        return new GetUserResponse(user);
    }

    @Transactional(readOnly = true)
    public boolean checkUserByEmail(String email) {
        Optional<User> result = userRepository.findByEmailAndState(email, ACTIVE);
        if (result.isPresent()) return true;
        return false;
    }

    public PostLoginResponse logIn(PostLoginRequest postLoginRequest) {
        User user = userRepository.findByEmailAndState(postLoginRequest.getEmail(), ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        String encryptPwd;
        try {
            encryptPwd = new SHA256().encrypt(postLoginRequest.getPassword());
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        if(user.getPassword().equals(encryptPwd)){
            Long userId = user.getId();
            String jwt = jwtService.createJwt(userId);
            return new PostLoginResponse(userId,jwt);
        } else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

    public GetUserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmailAndState(email, ACTIVE).orElseThrow(() -> new BaseException(NOT_FIND_USER));
        return new GetUserResponse(user);
    }
}
