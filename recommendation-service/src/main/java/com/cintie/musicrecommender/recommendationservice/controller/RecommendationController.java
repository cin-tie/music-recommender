package com.cintie.musicrecommender.recommendationservice.controller;

import com.cintie.musicrecommender.recommendationservice.clients.SpotifyServiceClient;
import com.cintie.musicrecommender.recommendationservice.dto.RecommendationResponse;
import com.cintie.musicrecommender.recommendationservice.dto.UserVector;
import com.cintie.musicrecommender.recommendationservice.services.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final SpotifyServiceClient spotifyServiceClient;

    @GetMapping("/top3")
    public List<RecommendationResponse> recommendations(@RequestHeader("X-User-Id") String spotifyId){
        return recommendationService.recommendations(spotifyId);
    }
}