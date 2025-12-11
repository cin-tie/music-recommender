package com.cintie.musicrecommender.authservice.controllers;

import com.cintie.musicrecommender.authservice.dto.ValidateRequest;
import com.cintie.musicrecommender.authservice.dto.ValidateResponse;
import com.cintie.musicrecommender.authservice.services.JwtService;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    JwtService jwtService;

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

    @PostMapping("/validate")
    public ResponseEntity<ValidateResponse> validate(@RequestBody ValidateRequest request){
        String token = request.token();
        if(jwtService.validateToken(token)){
            return ResponseEntity.ok(new ValidateResponse(true));
        }
        return ResponseEntity.status(302).header("Location", "/oauth2/authorization/spotify").build();
    }
}