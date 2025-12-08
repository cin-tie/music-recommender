package com.cintie.musicrecommender.authservice.controllers;

import com.cintie.musicrecommender.authservice.dto.TokenValidationRequest;
import com.cintie.musicrecommender.authservice.services.JwtService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;

    @PostMapping("/validate")
    public boolean validateToken(@RequestBody TokenValidationRequest request) {
        return jwtService.validateToken(request.token());
    }
}