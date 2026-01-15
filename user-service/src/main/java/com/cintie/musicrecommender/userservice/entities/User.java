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
    @Column(unique = true)
    private String id;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private Boolean explicitFilterEnabled;

    @Column(nullable = false)
    private Boolean explicitFilterLocked;

    @Column(nullable = false)
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
