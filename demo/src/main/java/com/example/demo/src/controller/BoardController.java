package com.example.demo.src.controller;

import com.example.demo.response.BaseResponse;
import com.example.demo.src.entity.Board;
import com.example.demo.src.repository.BoardRepository;
import com.example.demo.src.request.PatchBoardRequest;
import com.example.demo.src.request.PostBoardRequest;
import com.example.demo.src.response.GetBoardResponse;
import com.example.demo.src.response.PostBoardResponse;
import com.example.demo.src.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/app/boards")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/home")
    public String getBoardList(Model model) {
        model.addAttribute("boards", boardService.findAll());

        return "board";
    }

    @GetMapping("/write")
    public String write(Model model, @RequestParam(required = false) Long id) {
        if (id == null) {
            model.addAttribute("board", new PostBoardRequest());
            return "write";
        }

        GetBoardResponse getBoardResponse = boardService.getBoard(id);
        model.addAttribute("board", getBoardResponse);

        return "write";
    }

    @PostMapping("/write")
    public String getBoardForm(PostBoardRequest postBoardRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "redirect:home";
        }

        boardService.createBoard(postBoardRequest);

        return "redirect:home";
    }
}
