package com.cintie.musicrecommender.authservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
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

    @Column(nullable = false, unique = true)
    private String spotify_id;

    @Column(nullable = false)
    private String display_name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String access_token;

    @Column(nullable = false)
    private String refresh_token;

    @Column(nullable = false)
    private Instant token_expiry;

    @Column(nullable = false)
    private Instant created_at;

    @Column(nullable = false)
    private Instant updated_at;

    @PrePersist void onCreate(){
        created_at = Instant.now();
        updated_at = Instant.now();
    }

    @PreUpdate void onUpdate(){
        updated_at = Instant.now();
    }
}
