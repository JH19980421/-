package com.example.demo.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserResponse {
    private Long id;
    private String jwt;

    public PostUserResponse(Long id) {
        this.id = id;
    }
}
