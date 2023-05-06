package com.example.demo.src.service;

import com.example.demo.entity.BaseEntity;
import com.example.demo.exceptions.BaseException;
import com.example.demo.response.BaseResponseStatus;
import com.example.demo.src.entity.Board;
import com.example.demo.src.repository.BoardRepository;
import com.example.demo.src.request.PatchBoardRequest;
import com.example.demo.src.request.PostBoardRequest;
import com.example.demo.src.response.GetBoardResponse;
import com.example.demo.src.response.PostBoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.entity.BaseEntity.State.*;
import static com.example.demo.response.BaseResponseStatus.*;

@Transactional
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public PostBoardResponse createBoard(PostBoardRequest postBoardRequest) {
        if (null != postBoardRequest.getId()) {
            Board board = makeBoard(postBoardRequest.getId());
            board.modifyBoard(postBoardRequest);
            PostBoardResponse postBoardResponse = new PostBoardResponse(board.getId());

            return postBoardResponse;
        }

        Board board = new Board(postBoardRequest.getTitle(), postBoardRequest.getContent(), postBoardRequest.getWriter(), 0);
        boardRepository.save(board);
        PostBoardResponse postBoardResponse = new PostBoardResponse(board.getId());

        return postBoardResponse;
    }

    public GetBoardResponse getBoard(Long id) {
        Board board = makeBoard(id);

        GetBoardResponse getBoardResponse = new GetBoardResponse(board);

        return getBoardResponse;
    }

    public GetBoardResponse deleteBoard(Long id) {
        Board board = makeBoard(id);

        board.deleteBoard();

        GetBoardResponse getBoardResponse = new GetBoardResponse(board);

        return getBoardResponse;
    }

    public GetBoardResponse modifyBoard(Long id, PostBoardRequest postBoardRequest) {
        Board board = makeBoard(id);

        board.modifyBoard(postBoardRequest);

        GetBoardResponse getBoardResponse = new GetBoardResponse(board);

        return getBoardResponse;
    }

    public List<Board> findAll() {
        List<Board> boards = boardRepository.findAll();
        return boards;
    }

    private Board makeBoard(Long id) {
        Board board = boardRepository.findByIdAndState(id, ACTIVE).orElse(null);

        return board;
    }

}
