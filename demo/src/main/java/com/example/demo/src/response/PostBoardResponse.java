package com.example.demo.src.response;

import lombok.Getter;

@Getter
public class PostBoardResponse {

    private Long id;

    public PostBoardResponse(Long id) {
        this.id = id;
    }
}
