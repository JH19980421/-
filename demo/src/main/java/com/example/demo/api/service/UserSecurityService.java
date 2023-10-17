package com.example.demo.api.service;

import com.example.demo.api.entity.Member;
import com.example.demo.api.entity.MemberDetails;
import com.example.demo.api.entity.UserRole;
import com.example.demo.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.demo.web.entity.BaseEntity.State.ACTIVE;

import java.util.ArrayList;
import java.util.List;
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
        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
            return new MemberDetails(new User(member.getEmail(), member.getPassword(), authorities), member.getName());
        }

        authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        return new MemberDetails(new User(member.getEmail(), member.getPassword(), authorities), member.getName());
    }
}
