package com.cintie.musicrecommender.spotifyservice.filters;

import com.cintie.musicrecommender.spotifyservice.services.ServiceJwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InternalServiceJwtFilter extends OncePerRequestFilter {

    private final ServiceJwtService serviceJwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().startsWith("/internal")) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println(request.getRequestURI());
        System.out.println("1");

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        System.out.println("2");

        String token = header.substring(7);

        try {
            Claims claims = serviceJwtService.validateAndGetClaims(token);

            if (!"SERVICE".equals(claims.get("role"))) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            System.out.println("3");

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(),
                    null,
                    List.of(() -> "ROLE_SERVICE")
            );

            System.out.println("4");

            SecurityContextHolder.getContext().setAuthentication(auth);

            System.out.println("5");
            filterChain.doFilter(request, response);
            System.out.println("final");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setStatus(401);
        }
    }
}
