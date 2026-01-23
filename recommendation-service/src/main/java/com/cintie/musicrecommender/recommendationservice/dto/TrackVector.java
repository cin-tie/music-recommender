package com.cintie.musicrecommender.recommendationservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrackVector {

    private String trackId;

    private double acousticness;
    private double danceability;
    private double energy;
    private double instrumentalness;
    private int key;
    private double liveness;
    private double loudness;
    private int mode;
    private double speechiness;
    private double tempo;
    private int timeSignature;
    private double valence;
}
