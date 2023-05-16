package com.example.demo.api.repository;

import com.example.demo.api.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static com.example.demo.web.entity.BaseEntity.*;
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByIdAndState(Long id, State state);
    List<Board> findAllByWriterAndState(String Writer, State state);

    Page<Board> findByTitleContainingOrContentContainingOrWriterContaining(String title, String content, String writer, Pageable pageable);
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
    Page<Board> findByWriterContaining(String writer, Pageable pageable);
}
