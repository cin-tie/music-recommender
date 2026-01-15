package com.cintie.musicrecommender.userservice.services;

import com.cintie.musicrecommender.userservice.entities.User;
import com.cintie.musicrecommender.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getBySpotifyId(String spotifyId){
        return userRepository.findBySpotifyId(spotifyId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User save(User user){
        return userRepository.save(user);
    }
}
