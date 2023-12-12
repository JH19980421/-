package com.example.demo.api.service;

import com.example.demo.api.entity.Board;
import com.example.demo.api.entity.UploadFile;
import com.example.demo.api.repository.BoardRepository;
import com.example.demo.api.request.PostBoardRequest;
import com.example.demo.api.response.GetBoardResponse;
import com.example.demo.api.response.PostBoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.example.demo.web.entity.BaseEntity.State.*;


@RequiredArgsConstructor
@Service
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final UploadFileService uploadFileService;

    public PostBoardResponse createBoard(PostBoardRequest postBoardRequest) throws IOException {
        if (Objects.nonNull(postBoardRequest.getId())) {
            Board board = makeBoard(postBoardRequest.getId());

            // Null 익셉션
            modifyBoard(board.getId(), postBoardRequest);
            return new PostBoardResponse(board.getId());
        }

        Board board = new Board(postBoardRequest.getTitle(), postBoardRequest.getContent(), postBoardRequest.getWriter());
        List<UploadFile> uploadFiles = uploadFileService.storeFiles(postBoardRequest.getUploadFiles());
        board.addFilesToBoard(uploadFiles);

        boardRepository.save(board);
//        System.out.println("check : "+board.getFiles().isEmpty());
        return new PostBoardResponse(board.getId());
    }

    public GetBoardResponse getBoard(Long id) {
        Board board = makeBoard(id);
        System.out.println("check board : " + board.getUploadFiles().isEmpty());
        return new GetBoardResponse(board);
    }

    public GetBoardResponse deleteBoard(Long id) {
        Board board = makeBoard(id);

        board.deleteBoard();

        return new GetBoardResponse(board);
    }

    public GetBoardResponse modifyBoard(Long id, PostBoardRequest postBoardRequest) throws IOException{
        Board board = makeBoard(id);
        board.modifyBoard(postBoardRequest);
        board.addFilesToBoard(uploadFileService.storeFiles(postBoardRequest.getUploadFiles()));
        boardRepository.save(board);
        return new GetBoardResponse(board);
    }

    public List<Board> findAll() {
        List<Board> boards = boardRepository.findAll();
        return boards;
    }

    public Page<Board> findPage(Pageable pageable) {
        Page<Board> page = boardRepository.findAll(pageable);
        return page;
    }

    public Page<Board> findPageByTitleOrContentOrWriter(String title, String content, String writer, Pageable pageable) {
        Page<Board> page = boardRepository.findByTitleContainingOrContentContainingOrWriterContaining(title, content, writer, pageable);
        return page;
    }

    public Page<Board> findPageByTitleOrContent(String title, String content, Pageable pageable) {
        Page<Board> page = boardRepository.findByTitleContainingOrContentContaining(title, content, pageable);
        return page;
    }

    public Page<Board> findPageByWriter(String writer, Pageable pageable) {
        Page<Board> page = boardRepository.findByWriterContaining(writer, pageable);
        return page;
    }

    private Board makeBoard(Long id) {
        return boardRepository.findByIdAndState(id, ACTIVE).orElse(null);
    }
}
