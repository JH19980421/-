package com.example.demo.src.repository;

import com.example.demo.src.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.entity.BaseEntity.*;
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByIdAndState(Long id, State state);
    List<Board> findAllByWriterAndState(String Writer, State state);
}
