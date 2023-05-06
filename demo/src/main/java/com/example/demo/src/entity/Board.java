package com.example.demo.src.entity;

import com.example.demo.entity.BaseEntity;
import com.example.demo.src.request.PatchBoardRequest;
import com.example.demo.src.request.PostBoardRequest;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 1, max = 100)
    private String title;
    @NotNull
    @Size(min = 1, max = 500)
    private String content;
    @NotNull
    @Size(min = 1, max = 10)
    private String writer;
    private int views;

    public Board(String title, String content, String writer, int views) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.views = views;
    }

    public void deleteBoard() {
        this.state = State.INACTIVE;
    }

    public void modifyBoard(PostBoardRequest postBoardRequest) {
        this.title = postBoardRequest.getTitle();
        this.content = postBoardRequest.getContent();
    }

    public void countView() {
        this.views++;
    }
}
