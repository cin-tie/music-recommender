package com.cintie.musicrecommender.spotifyservice.services;

import com.cintie.musicrecommender.spotifyservice.dto.AudioFeatures;
import com.cintie.musicrecommender.spotifyservice.dto.UserVector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyUserVectorService {

    private final UserVectorCacheService userVectorCacheService;
    private final AudioFeaturesService audioFeaturesService;
    private final SpotifyService spotifyService;
    private final UserVectorBuilder userVectorBuilder;
    private final UserVectorNormalizer userVectorNormalizer;

    public UserVector getUserVector(String spotifyId){
        UserVector cached = userVectorCacheService.get(spotifyId);

        if(cached != null)
            return cached;

        List<String> trackIds = spotifyService.getRecentTrackIds(spotifyId);
        List<AudioFeatures> audioFeatures = audioFeaturesService.getForTracks(spotifyId, trackIds);

        UserVector userVector = userVectorBuilder.build(audioFeatures);
        userVector = userVectorNormalizer.normalize(userVector);

        userVectorCacheService.save(spotifyId, userVector);
        return userVector;
    }
}
