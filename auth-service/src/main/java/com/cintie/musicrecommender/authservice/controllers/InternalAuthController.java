package com.cintie.musicrecommender.authservice.controllers;

import com.cintie.musicrecommender.authservice.dto.AccessTokenResponse;
import com.cintie.musicrecommender.authservice.entities.User;
import com.cintie.musicrecommender.authservice.repositories.UserRepository;
import com.cintie.musicrecommender.authservice.services.SpotifyTokenRefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/internal/auth")
@RequiredArgsConstructor
public class InternalAuthController {

    private final UserRepository userRepository;
    private final SpotifyTokenRefreshService spotifyTokenRefreshService;

    @GetMapping("/spotify-token/{spotifyId}")
    public AccessTokenResponse getSpotifyToken(@PathVariable String spotifyId){
        User user = userRepository.findBySpotifyId(spotifyId).orElseThrow();

        if(user.getTokenExpiry().isBefore(Instant.now().plusSeconds(30))){
            user = spotifyTokenRefreshService.refresh(user);
        }

        return new AccessTokenResponse(user.getAccessToken(), user.getTokenExpiry());
    }
}
