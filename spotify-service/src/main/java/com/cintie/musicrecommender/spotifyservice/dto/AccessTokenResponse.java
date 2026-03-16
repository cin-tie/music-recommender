package com.cintie.musicrecommender.spotifyservice.dto;

import java.time.Instant;

public record AccessTokenResponse(String accessToken, Instant tokenExpiry) {
}
