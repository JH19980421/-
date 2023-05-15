package com.example.demo.web;

import com.example.demo.api.entity.Board;
import com.example.demo.api.repository.BoardRepository;
import com.example.demo.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class startDB {
    private final InitService initService;

    @PostConstruct
    public void initializer() {
        initService.initBoard();
    }

//    @Component
    @Transactional
    @RequiredArgsConstructor
    @Service
    static class InitService {
        private final BoardRepository boardRepository;
        public void initBoard() {
            for (int i = 1; i <= 100; i++) {
                Board board = new Board("제목" + i,"내용" + i,"작성자" + i);
                boardRepository.save(board);
            }
        }
    }
}
