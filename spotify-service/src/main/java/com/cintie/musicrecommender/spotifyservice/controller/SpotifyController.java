package com.cintie.musicrecommender.spotifyservice.controller;

import com.cintie.musicrecommender.spotifyservice.dto.TrackVector;
import com.cintie.musicrecommender.spotifyservice.dto.UserVector;
import com.cintie.musicrecommender.spotifyservice.services.SpotifyRecommendationService;
import com.cintie.musicrecommender.spotifyservice.services.SpotifyService;
import com.cintie.musicrecommender.spotifyservice.services.SpotifyUserVectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spotify")
@RequiredArgsConstructor
public class SpotifyController {

    private final SpotifyService spotifyService;
    private final SpotifyUserVectorService spotifyUserVectorService;
    private final SpotifyRecommendationService spotifyRecommendationService;

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
