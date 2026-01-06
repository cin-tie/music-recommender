package com.cintie.musicrecommender.authservice.dto;

import java.time.Instant;

public record ProfileResponse(String spotifyId, String email, String displayName, Instant createdAt, Instant updatedAt) {
}
