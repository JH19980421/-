package com.example.demo.api.service;

import com.example.demo.api.entity.Member;
import com.example.demo.api.entity.MemberDetails;
import com.example.demo.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.demo.web.entity.BaseEntity.State.ACTIVE;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> checkUser = userRepository.findByEmailAndState(username, ACTIVE);

        if (checkUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }

        Member member = checkUser.get();
        return new MemberDetails(member.getEmail(), member.getPassword());
    }
}
