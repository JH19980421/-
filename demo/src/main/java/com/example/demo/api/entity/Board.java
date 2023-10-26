package com.example.demo.api.entity;

import com.example.demo.web.entity.BaseEntity;
import com.example.demo.api.request.PostBoardRequest;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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
    @OneToMany(mappedBy = "board")
    private List<File> files;
    @Column
    private int views = 0;

    public Board(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void addFilesToBoard(List<File> files) {
        this.files = files;
    }

    public void deleteBoard() {
        this.state = State.INACTIVE;
    }

    public void modifyBoard(PostBoardRequest postBoardRequest) {
        this.title = postBoardRequest.getTitle();
        this.content = postBoardRequest.getContent();
    }

    public void incrementView() {
        this.views++;
    }
}
