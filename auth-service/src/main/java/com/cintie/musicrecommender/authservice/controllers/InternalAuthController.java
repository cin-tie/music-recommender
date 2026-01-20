package com.cintie.musicrecommender.authservice.controllers;

import com.cintie.musicrecommender.authservice.dto.AccessTokenResponse;
import com.cintie.musicrecommender.authservice.entities.User;
import com.cintie.musicrecommender.authservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/auth")
@RequiredArgsConstructor
public class InternalAuthController {

    private final UserRepository userRepository;

    @GetMapping("/spotify-token/{spotifyId}")
    public AccessTokenResponse getSpotifyToken(@PathVariable String spotifyId){
        User user = userRepository.findBySpotifyId(spotifyId).orElseThrow();

        return new AccessTokenResponse(user.getAccessToken(), user.getTokenExpiry());
    }
}
