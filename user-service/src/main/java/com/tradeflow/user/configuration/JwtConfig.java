package com.tradeflow.user.configuration;


import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.oauth2.jwt.*;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-seconds}")
    private long expirationSeconds;

    /**
     * В качестве JWKSource используем единственный OctetSequenceKey с заданным keyID.
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        // 1) Собираем SecretKey из строки
        SecretKey secretKey = new SecretKeySpec(
                jwtSecret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );

        // 2) Строим JWK с алгоритмом HS256 и уникальным keyID
        OctetSequenceKey jwk = new OctetSequenceKey.Builder(secretKey)
                .algorithm(JWSAlgorithm.HS256)
                .keyID(UUID.randomUUID().toString())   // <— обязательно задаём kid
                .build();

        // 3) JWKSet и JWKSource
        JWKSet jwkSet = new JWKSet(jwk);
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(jwkSet);

        // 4) Возвращаем NimbusJwtEncoder с этим JWKSource
        return new NimbusJwtEncoder(jwkSource);
    }

    /**
     * Простой JwtDecoder по тому же секретному ключу.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey secretKey = new SecretKeySpec(
                jwtSecret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    /**
     * Утилитарный метод для генерации набора claims.
     */
    public JwtClaimsSet buildClaims(String subject) {
        Instant now = Instant.now();
        return JwtClaimsSet.builder()
                .issuer("your-app")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationSeconds))
                .subject(subject)
                .build();
    }
}
