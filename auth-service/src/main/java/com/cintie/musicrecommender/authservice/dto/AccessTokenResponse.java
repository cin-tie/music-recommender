package com.cintie.musicrecommender.authservice.dto;

import java.time.Instant;

public record AccessTokenResponse(String accessToken, Instant tokenExpiry) {
}
