package com.example.demo.api.repository;

import com.example.demo.api.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static com.example.demo.web.entity.BaseEntity.State;
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByIdAndState(Long id, State state);
    Optional<ChatRoom> findByNameAndState(String name, State state);
}
