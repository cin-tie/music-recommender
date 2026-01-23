package com.cintie.musicrecommender.spotifyservice.controller;

import com.cintie.musicrecommender.spotifyservice.services.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spotify")
@RequiredArgsConstructor
public class SpotifyController {

    private final SpotifyService spotifyService;

    @GetMapping("/recent")
    public String recent(@RequestHeader("X-User-Id") String spotifyId){
        return spotifyService.getRecent(spotifyId);
    }

    @GetMapping("/top/tracks")
    public String topTracks(@RequestHeader("X-User-Id") String spotifyId){
        return spotifyService.getTopTracks(spotifyId);
    }

    @GetMapping("/top/artists")
    public String topArtists(@RequestHeader("X-User-Id") String spotifyId){
        return spotifyService.getTopArtists(spotifyId);
    }

    @GetMapping("/saved/tracks")
    public String savedTracks(@RequestHeader("X-User-Id") String spotifyId){
        return spotifyService.getSavedTracks(spotifyId);
    }
}
