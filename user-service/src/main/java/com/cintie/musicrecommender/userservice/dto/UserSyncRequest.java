package com.cintie.musicrecommender.userservice.dto;

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
