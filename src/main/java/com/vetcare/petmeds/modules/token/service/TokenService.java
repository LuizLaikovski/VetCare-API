package com.vetcare.petmeds.modules.token.service;

import com.vetcare.petmeds.modules.user.entity.UserEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    @CachePut(value = "tokens", key = "#token")
    public String tokenCreate(UserEntity userEntity, String token) {
        return token;
    }
    
    // Método auxiliar para criar e armazenar o token associado ao user
    public String generateAndStoreToken(UserEntity userEntity) {
        String token = UUID.randomUUID().toString();
        tokenCreate(userEntity, token);
        return token;
    }

    @Cacheable(value = "tokens", key = "#token")
    public boolean isValid(String token) {
        return false; // Se chegar aqui, o token não está no cache
    }

    @CacheEvict(value = "tokens", key = "#token")
    public void deleteToken(String token) {
        // Apenas remove do cache
    }
}
