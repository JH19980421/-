package com.example.demo.api.entity;

import com.example.demo.web.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class File extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    private String originalName;
    private String saveName;

    public File(String originalName, String saveName) {
        this.originalName = originalName;
        this.saveName = saveName;
    }
}
