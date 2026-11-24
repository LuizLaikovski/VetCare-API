package com.vetcare.petmeds.applicationPrimary.token.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private static final String CACHE_NAME = "active-tokens";

    private final CacheManager cacheManager;

    @Value("${api.security.token.secret}")
    private String secret;

    public TokenService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    @CachePut(value = CACHE_NAME, key = "#userEntity.email")
    public String generateAndStoreToken(UserEntity userEntity) {
        String token = JWT.create()
                .withIssuer("vetcare-api")
                .withSubject(userEntity.getEmail())
                .withExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS))
                .sign(getAlgorithm());
        logger.info("Token gerado com sucesso para usuário: {}", userEntity.getEmail());
        return token;
    }

    @Cacheable(value = "tokens-blacklist", key = "#token")
    public boolean isRevoked(String token) {
        return false; // Not in cache = not revoked
    }

    public String validateToken(String token) {
        try {
            String subject = JWT.require(getAlgorithm())
                    .withIssuer("vetcare-api")
                    .build()
                    .verify(token)
                    .getSubject();

            Cache cache = cacheManager.getCache(CACHE_NAME);
            if (cache != null) {
                String activeToken = cache.get(subject, String.class);
                if (activeToken == null || !activeToken.equals(token)) {
                    logger.warn("Tentativa de uso de token antigo ou revogado para o usuário: {}", subject);
                    return null;
                }
            }


            logger.info("Token validado com sucesso");
            return subject;
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public void revokeToken(String token) {
        try {
            // Extrai o e-mail (subject) do token para remover a sessão ativa do cache
            String subject = JWT.require(getAlgorithm())
                    .withIssuer("vetcare-api")
                    .build()
                    .verify(token)
                    .getSubject();

            Cache cache = cacheManager.getCache(CACHE_NAME);
            if (cache != null && subject != null) {
                cache.evict(subject);
                logger.info("Token revogado com sucesso para o usuário: {}", subject);
            }
        } catch (JWTVerificationException exception) {
            logger.warn("Tentativa de revogar um token já inválido ou expirado.");
        }
    }
}
