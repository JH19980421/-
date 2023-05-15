package com.example.demo.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class PostBoardRequest {

    private Long id;
    @Size(min = 1, max = 100)
    private String title;
    @Size(min = 1, max = 500)
    private String content;
    @Size(min = 1, max = 10)
    private String writer;

}
