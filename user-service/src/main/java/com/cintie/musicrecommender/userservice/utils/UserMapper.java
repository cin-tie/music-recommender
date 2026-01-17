package com.cintie.musicrecommender.userservice.utils;

import com.cintie.musicrecommender.userservice.dto.UserProfileResponse;
import com.cintie.musicrecommender.userservice.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserProfileResponse map(User user) {
        return new UserProfileResponse(
                user.getSpotifyId(),
                user.getDisplayName(),
                user.getEmail(),
                user.getCountry(),
                user.getProduct(),
                user.getExplicitFilterEnabled(),
                user.getExplicitFilterLocked(),
                user.getFollowers(),
                user.getImageUrl()
        );
    }
}
