package com.example.demo.src.entity;

import com.example.demo.OAuth.SocialLoginType;
import com.example.demo.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(name = "Member") // Table 이름을 명시해주지 않으면 class 이름을 Table 이름으로 대체한다.
public class User extends BaseEntity {
    @Id // PK를 의미하는 어노테이션
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialLoginType isOAuth;

    @Builder
    public User(Long id, String email, String password, String name, SocialLoginType isOAuth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.isOAuth = isOAuth;
    }

    public void updateName(String name) {
        this.name = name;
    }
    public void deleteUser() {
        this.state = State.INACTIVE;
    }

}
