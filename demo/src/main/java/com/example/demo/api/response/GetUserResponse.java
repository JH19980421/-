package com.example.demo.api.response;


import com.example.demo.api.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetUserResponse {
    private Long id;
    private String email;
    private String name;

    public GetUserResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
    }
}
