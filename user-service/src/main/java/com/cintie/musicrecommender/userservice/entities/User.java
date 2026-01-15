package com.cintie.musicrecommender.userservice.entities;

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
    @Column(nullable = false, updatable = false)
    private String spotifyId;

    @Column
    private String displayName;

    @Column
    private String email;

    @Column
    private String country;

    @Column
    private String product;

    @Column
    private Boolean explicitFilterEnabled;

    @Column
    private Boolean explicitFilterLocked;

    @Column
    private Integer followers;

    @Column
    private String imageUrl;

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
