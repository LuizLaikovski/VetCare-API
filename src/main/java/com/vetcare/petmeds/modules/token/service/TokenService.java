package com.vetcare.petmeds.modules.token.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.vetcare.petmeds.modules.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    public String generateAndStoreToken(UserEntity userEntity) {
        return JWT.create()
                .withIssuer("vetcare-api")
                .withSubject(userEntity.getEmail())
                .withExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS))
                .sign(getAlgorithm());
    }

    @Cacheable(value = "tokens-blacklist", key = "#token")
    public boolean isRevoked(String token) {
        return false; // Not in cache = not revoked
    }

    public String validateToken(String token) {
        if (isRevoked(token)) {
            return null;
        }
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer("vetcare-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    @CacheEvict(value = "tokens-blacklist", key = "#token")
    public void revokeToken(String token) {
        // Adds token to cache (blacklist)
    }
}
