package com.cintie.musicrecommender.authservice.security;

import com.cintie.musicrecommender.authservice.entities.User;
import com.cintie.musicrecommender.authservice.repositories.UserRepository;
import com.cintie.musicrecommender.authservice.services.JwtService;
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

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final OAuth2AuthorizedClientService clientService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        OAuth2AuthorizedClient client = clientService.loadAuthorizedClient("spotify", authentication.getName());
        String accessToken = client.getAccessToken().getTokenValue();
        String refreshToken = client.getRefreshToken().getTokenValue();
        Instant expiresAt = client.getAccessToken().getExpiresAt();

        String spotifyId =oAuth2User.getAttribute("id");
        String email = oAuth2User.getAttribute("email");
        String displayName = oAuth2User.getAttribute("display_name");

        User user = userRepository.findBySpotifyId(spotifyId).orElse(User.builder().spotifyId(spotifyId).build());
        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshToken);
        user.setEmail(email);
        user.setDisplayName(displayName);
        user.setTokenExpiry(expiresAt);

        userRepository.save(user);
        String jwt = jwtService.generateToken(user);

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), new JwtResponse(jwt));
    }

    private record JwtResponse(String token) {}
}
