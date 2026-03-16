package com.cintie.musicrecommender.recommendationservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackVector {
    private String trackId;
    private String name;
    private String artist;

    private double acousticness;
    private double danceability;
    private double energy;
    private double instrumentalness;
    private double liveness;
    private double loudness;
    private double speechiness;
    private double tempo;
    private double valence;
}
