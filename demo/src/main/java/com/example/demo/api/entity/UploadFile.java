package com.example.demo.api.entity;

import com.example.demo.web.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UploadFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;
    private String originalName;
    private String saveName;
    private String savePath;

    public UploadFile(String originalName, String saveName, String savePath) {
        this.originalName = originalName;
        this.saveName = saveName;
        this.savePath = savePath;
    }


}
