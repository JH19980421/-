package com.example.demo.api.entity;

import com.example.demo.web.OAuth.SocialLoginType;
import com.example.demo.web.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(name = "Member")
public class Member extends BaseEntity {
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

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public Member(String email, String password, String name, SocialLoginType isOAuth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.isOAuth = isOAuth;
    }

    public static Member createUser(String email, String password, String name, SocialLoginType isOAuth) {

        return new Member(email, password, name, isOAuth);
    }

    public void updateName(String name) {
        this.name = name;
    }
    public void inActive() {
        this.state = State.INACTIVE;
    }

    @Getter
    private enum UserRole {
        ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

        UserRole(String value) {
            this.value = value;
        }

        private String value;
    }
}
