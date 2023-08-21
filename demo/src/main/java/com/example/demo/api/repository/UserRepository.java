package com.example.demo.api.repository;

import com.example.demo.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.web.entity.BaseEntity.*;

public interface UserRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByIdAndState(Long id, State state);
    Optional<Member> findByEmailAndState(String email, State state);

    List<Member> findAllByEmailAndState(String email, State state);
    List<Member> findAllByState(State state);
}
