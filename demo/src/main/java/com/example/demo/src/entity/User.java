package com.example.demo.src.entity;

import com.example.demo.OAuth.SocialLoginType;
import com.example.demo.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(name = "Member")
public class User extends BaseEntity {
    @Id
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
