package com.example.demo.api.repository;

import com.example.demo.api.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.example.demo.web.entity.BaseEntity.State;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Optional<ChatMessage> findByIdAndState(Long id, State state);

}
