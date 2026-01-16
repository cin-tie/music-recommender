package com.cintie.musicrecommender.authservice.security;

import com.cintie.musicrecommender.authservice.dto.UserSyncRequest;
import com.cintie.musicrecommender.authservice.entities.User;
import com.cintie.musicrecommender.authservice.repositories.UserRepository;
import com.cintie.musicrecommender.authservice.services.UserJwtService;
import com.cintie.musicrecommender.authservice.services.UserServiceClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final OAuth2AuthorizedClientService clientService;
    private final UserRepository userRepository;
    private final UserJwtService userJwtService;
    private final UserServiceClient userServiceClient;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient("spotify", authentication.getName());
        if (client == null || client.getAccessToken() == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "OAuth2 client not found");
            return;
        }

        String accessToken = client.getAccessToken().getTokenValue();
        String refreshToken = client.getRefreshToken() != null ? client.getRefreshToken().getTokenValue() : null;
        Instant expiresAt = client.getAccessToken().getExpiresAt();

        String spotifyId =oAuth2User.getAttribute("id");
        if (spotifyId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Spotify user ID not found");
            return;
        }
    
        String email = oAuth2User.getAttribute("email");
        String displayName = oAuth2User.getAttribute("display_name");

        String country = oAuth2User.getAttribute("country");
        String product = oAuth2User.getAttribute("product");

        Map<String, Object> explicit = oAuth2User.getAttribute("explicit_content");
        Boolean filterEnabled = explicit != null ? (Boolean) explicit.get("filter_enabled") : null;
        Boolean filterLocked = explicit != null ? (Boolean) explicit.get("filter_locked") : null;

        Map<String, Object> followersMap = oAuth2User.getAttribute("followers");
        Integer followers = followersMap != null ? (Integer) followersMap.get("total") : null;

        List<Map<String, Object>> images = oAuth2User.getAttribute("images");
        String imageUrl = (images != null && !images.isEmpty())
                ? (String) images.get(0).get("url")
                : null;

        userServiceClient.sync(new UserSyncRequest(
                spotifyId,
                displayName,
                email,
                country,
                product,
                filterEnabled,
                filterLocked,
                followers,
                imageUrl
        ));

        User user = userRepository.findBySpotifyId(spotifyId).orElse(User.builder().spotifyId(spotifyId).build());
        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshToken);
        user.setEmail(email);
        user.setDisplayName(displayName);
        user.setTokenExpiry(expiresAt);

        userRepository.save(user);
        String jwt = userJwtService.generateToken(user);

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), new JwtResponse(jwt));
    }

    private record JwtResponse(String token) {}
}
