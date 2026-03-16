package com.cintie.musicrecommender.authservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String spotifyId;

    @Column(nullable = false, columnDefinition = "text")
    private String displayName;

    @Column(nullable = false, columnDefinition = "text")
    private String email;

    @Column(nullable = false, columnDefinition = "text")
    private String accessToken;

    @Column(nullable = false, columnDefinition = "text")
    private String refreshToken;

    @Column(nullable = false)
    private Instant tokenExpiry;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist void onCreate(){
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate void onUpdate(){
        updatedAt = Instant.now();
    }
}
