package com.cintie.musicrecommender.authservice.controllers;

import com.cintie.musicrecommender.authservice.dto.ProfileRequest;
import com.cintie.musicrecommender.authservice.dto.ProfileResponse;
import com.cintie.musicrecommender.authservice.dto.ValidateRequest;
import com.cintie.musicrecommender.authservice.dto.ValidateResponse;
import com.cintie.musicrecommender.authservice.entities.User;
import com.cintie.musicrecommender.authservice.repositories.UserRepository;
import com.cintie.musicrecommender.authservice.services.JwtService;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private JwtService jwtService;
    private UserRepository userRepository;

    public AuthController() {
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("test");
    }

    @GetMapping("/login")
    public ResponseEntity<Void> login(){
        return ResponseEntity.status(302).header("Location", "/oauth2/authorization/spotify").build();
    }

    @GetMapping("/validate")
    public ResponseEntity<ValidateResponse> validate(@RequestBody ValidateRequest request){
        String token = request.token();
        if(jwtService.validateToken(token)){
            return ResponseEntity.ok(new ValidateResponse(true));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> profile(@RequestBody ProfileRequest request){
    }
}