package com.cintie.musicrecommender.spotifyservice.controller;

import com.cintie.musicrecommender.spotifyservice.dto.UserVector;
import com.cintie.musicrecommender.spotifyservice.services.SpotifyUserVectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/spotify")
@RequiredArgsConstructor
public class InternalSpotifyController {

    private final SpotifyUserVectorService spotifyUserVectorService;

    @GetMapping("/user-vector/{spotifyId}")
    public UserVector getUserVector(@PathVariable String spotifyId){
        return spotifyUserVectorService.getUserVector(spotifyId);
    }
}
