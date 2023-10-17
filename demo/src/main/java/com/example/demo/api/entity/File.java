package com.example.demo.api.entity;

import com.example.demo.web.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class File extends BaseEntity {
    private Long id;
    private Long boardId;
    private String originalName;
    private String saveName;
    private long size;

    public File(String originalName, String saveName, long size) {
        this.originalName = originalName;
        this.saveName = saveName;
        this.size = size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
