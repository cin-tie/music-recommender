package com.cintie.musicrecommender.spotifyservice.services;

import com.cintie.musicrecommender.spotifyservice.dto.AudioFeatures;
import com.cintie.musicrecommender.spotifyservice.dto.UserVector;
import com.cintie.musicrecommender.spotifyservice.utils.UserVectorBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotifyUserVectorService {

    // TODO: Analyse also saved, followed artists etc

    private final UserVectorCacheService userVectorCacheService;
    private final AudioFeaturesService audioFeaturesService;
    private final SpotifyService spotifyService;
    private final UserVectorBuilder userVectorBuilder;
    private final UserVectorNormalizer userVectorNormalizer;

    public UserVector getUserVector(String spotifyId){
        UserVector cached = userVectorCacheService.get(spotifyId);

        if(cached != null)
            return cached;

        System.out.println("6");
        List<String> trackIds = spotifyService.getRecentTrackIds(spotifyId);
        System.out.println("7");
        List<AudioFeatures> audioFeatures = audioFeaturesService.getForTracks(spotifyId, trackIds);

        UserVector userVector = userVectorBuilder.build(audioFeatures);
        userVector = userVectorNormalizer.normalize(userVector);

        userVectorCacheService.save(spotifyId, userVector);
        return userVector;
    }
}
