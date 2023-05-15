package com.example.demo.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class PatchUserRequest {
    private String name;

    public String getName() {
        return name;
    }
}
