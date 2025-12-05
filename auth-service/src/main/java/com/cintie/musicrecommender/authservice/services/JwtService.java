package com.cintie.musicrecommender.authservice.services;

import com.cintie.musicrecommender.authservice.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private String secret;
    private long expirationMs;

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getSpotify_id())
                .claim("email", user.getEmail())
                .claim("name", user.getDisplay_name())
                .claim("role", "USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
