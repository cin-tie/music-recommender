package com.cintie.musicrecommender.authservice.dto;

public record UserSyncRequest(
        String spotifyId,
        String displayName,
        String email,
        String country,
        String product,
        Boolean explicitFilterEnabled,
        Boolean explicitFilterLocked,
        Integer followers,
        String imageUrl
) {}
