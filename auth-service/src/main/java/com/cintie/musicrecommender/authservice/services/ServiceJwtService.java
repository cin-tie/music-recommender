package com.cintie.musicrecommender.authservice.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ServiceJwtService {

    @Value("${jwt.service.secret}")
    private String secret;

    @Value("${jwt.service.expiration}")
    private long expirationMs;

    public String generateServiceToken(String serviceName){
        return Jwts.builder()
                .setSubject(serviceName)
                .claim("role", "SERVICE")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
