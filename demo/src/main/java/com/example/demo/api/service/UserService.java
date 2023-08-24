package com.example.demo.api.service;

import com.example.demo.web.OAuth.SocialLoginType;
import com.example.demo.web.exceptions.BaseException;
import com.example.demo.api.entity.Member;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.web.OAuth.SocialLoginType.LOCAL;
import static com.example.demo.web.entity.BaseEntity.State.ACTIVE;
import static com.example.demo.web.response.BaseResponseStatus.*;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    //POST
    public PostUserResponse createUser(PostUserRequest postUserRequest) {
        //중복 체크
        Optional<Member> member = userRepository.findByEmailAndState(postUserRequest.getEmail(), ACTIVE);

        if (member.isPresent()) throw new BaseException(DUPLICATED_EMAIL);

//        String encryptPwd = new SHA256().encrypt(postUserRequest.getPassword());

        Member saveMember = Member.createUser(postUserRequest.getEmail(),
                passwordEncoder.encode(postUserRequest.getPassword()), postUserRequest.getName(), LOCAL);
        userRepository.save(saveMember);
        return new PostUserResponse(saveMember.getId());
    }

    public PostUserResponse createOAuthUser(Member member) {
        Member saveMember = userRepository.save(member);

        // JWT 발급
        String jwtToken = jwtService.createJwt(saveMember.getId());
        return new PostUserResponse(saveMember.getId(), jwtToken);
    }

    public void modifyUserName(Long userId, PatchUserRequest patchUserRequest) {
        Member member = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        member.updateName(patchUserRequest.getName());
    }

    public void deleteUser(Long userId) {
        Member member = userRepository.findByIdAndState(userId, ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
        member.inActive();
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
        Optional<Member> user = userRepository.findByEmailAndState(email, ACTIVE);

        return user.isPresent();
    }

    public PostLoginResponse logIn(PostLoginRequest postLoginRequest) {
        Member member = userRepository.findByEmailAndState(postLoginRequest.getEmail(), ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));

        if (isNotMatchedPassword(postLoginRequest.getPassword(), member.getPassword())) {
            throw new BaseException(FAILED_TO_LOGIN);
        }

        return new PostLoginResponse(
                member.getId(),
                jwtService.createJwt(member.getId())
        );
    }

    private boolean isNotMatchedPassword(String plainPassword, String encryptPassword) {

        String encrypt = new SHA256().encrypt(plainPassword);
        return !encrypt.equals(encryptPassword);
    }

    public GetUserResponse getUserByEmail(String email) {
        Member member = userRepository.findByEmailAndState(email, ACTIVE).orElseThrow(() -> new BaseException(NOT_FIND_USER));
        return new GetUserResponse(member);
    }
}
