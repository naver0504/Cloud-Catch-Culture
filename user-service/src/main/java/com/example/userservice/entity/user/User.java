package com.example.userservice.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class User {

    @Id @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String storedProfileImageUrl;

    @Column
    private int point;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    @Column(nullable = false)
    private String nickname;

    @PrePersist
    public void prePersist() {
        this.point = 0;
    }

    public static User createUser(final long userId) {
        return User.builder()
                .id(userId)
                .build();
    }


}
