package com.tradeflow.user.controller;

import com.tradeflow.user.configuration.JwtConfig;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtEncoder jwtEncoder;
    private long expirationSeconds = 3600;
    private final JwtConfig jwtConfig;

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody Map<String,String> creds) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.get("username"), creds.get("password")
                )
        );
        // создаём клеймы
        JwtClaimsSet claims = jwtConfig.buildClaims(auth.getName());
        // генерим токен
        String token = jwtEncoder.encode(
                JwtEncoderParameters.from(claims)
        ).getTokenValue();

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", auth.getName()
        ));
    }
}