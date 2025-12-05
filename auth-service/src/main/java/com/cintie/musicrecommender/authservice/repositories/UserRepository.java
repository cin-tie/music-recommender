package com.cintie.musicrecommender.authservice.repositories;

import com.cintie.musicrecommender.authservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySpotify_id(String spotify_id);
}
