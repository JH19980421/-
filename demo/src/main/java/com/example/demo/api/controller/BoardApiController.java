package com.example.demo.api.controller;

import com.example.demo.web.response.BaseResponse;
import com.example.demo.api.request.PostBoardRequest;
import com.example.demo.api.response.GetBoardResponse;
import com.example.demo.api.response.PostBoardResponse;
import com.example.demo.api.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/app/boards/api")
public class BoardApiController {
    private final BoardService boardService;

    @PostMapping("")
    public BaseResponse<PostBoardResponse> createBoard(@RequestBody PostBoardRequest postBoardRequest) throws IOException {
        PostBoardResponse postBoardResponse = boardService.createBoard(postBoardRequest);

        return new BaseResponse<>(postBoardResponse);
    }

    @GetMapping("/{id}")
    public BaseResponse<GetBoardResponse> getBoard(@PathVariable Long id) {
        GetBoardResponse getBoardResponse = boardService.getBoard(id);

        return new BaseResponse<>(getBoardResponse);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<GetBoardResponse> deleteBoard(@PathVariable Long id) {
        GetBoardResponse getBoardResponse = boardService.deleteBoard(id);

        return new BaseResponse<>(getBoardResponse);
    }

    @PatchMapping("/{id}")
    public BaseResponse<GetBoardResponse> modifyBoard(@PathVariable Long id, @RequestBody PostBoardRequest postBoardRequest) {
        GetBoardResponse getBoardResponse = boardService.modifyBoard(id, postBoardRequest);

        return new BaseResponse<>(getBoardResponse);
    }
}
