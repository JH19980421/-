package com.example.demo.api.controller;

import com.example.demo.api.entity.Board;
import com.example.demo.api.request.PostBoardRequest;
import com.example.demo.api.response.GetBoardResponse;
import com.example.demo.api.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/app/boards")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/home")
    public String getBoardList(Model model, @PageableDefault(size = 20) Pageable pageable,
                               @RequestParam(required = false, defaultValue = "") String search,
                               @RequestParam(required = false, defaultValue = "") String selectValue) {

        Page<Board> page;

        switch (selectValue) {
            case "1" :
                page = boardService.findPageByTitleOrContentOrWriter(search,search,search,pageable);
                break;
            case "2" :
                page = boardService.findPageByTitleOrContent(search, search, pageable);
                break;
            case "3" :
                page = boardService.findPageByWriter(search, pageable);
                break;
            default: page = boardService.findPage(pageable);
        }

        model.addAttribute("boards", page);

        int startPage = 5 * (page.getNumber() / 5) + 1;
        int endPage = Math.min(page.getTotalPages(), startPage + 4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board";
    }

    @GetMapping("/write")
    public String write(Model model, @RequestParam(required = false) Long id) {
        if (Objects.isNull(id)) {
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
