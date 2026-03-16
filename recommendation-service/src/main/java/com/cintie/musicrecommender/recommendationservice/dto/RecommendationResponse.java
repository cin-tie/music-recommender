package com.cintie.musicrecommender.recommendationservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationResponse {
    private String trackId;
    private String name;
    private String artist;

    private double similarity;
}
