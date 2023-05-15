package com.example.demo.api.repository;

import com.example.demo.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.web.entity.BaseEntity.*;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndState(Long id, State state);
    Optional<User> findByEmailAndState(String email, State state);

    List<User> findAllByEmailAndState(String email, State state);
    List<User> findAllByState(State state);
}
