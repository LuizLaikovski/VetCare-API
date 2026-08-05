package com.vetcare.petmeds.modules.token.service;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.modules.token.entity.TokenEntity;
import com.vetcare.petmeds.modules.token.repository.TokenRepository;
import com.vetcare.petmeds.modules.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    @Transactional
    public String tokenCreate(UserEntity userEntity) {
        TokenEntity tokenEntity = userEntity.getToken();
        if  (tokenEntity == null) {
            tokenEntity = new TokenEntity();
            tokenEntity.setUser(userEntity);
        }

        tokenEntity.setToken(UUID.randomUUID().toString());
        tokenEntity.setExpiredAt(LocalDateTime.now().plusHours(2));

        tokenRepository.save(tokenEntity);
        return tokenEntity.getToken();
    }

    public boolean isTokenExpired(TokenEntity tokenEntity) {
        return tokenEntity.getExpiredAt().isBefore(LocalDateTime.now());
    }

    @Transactional
    public String renewToken(String token) {
        TokenEntity tokenEntity = tokenRepository.findByToken(token).orElseThrow(() ->
                new ResourceNotFoundException("Token não encontrado"));
        
        tokenEntity.setToken(UUID.randomUUID().toString());
        tokenEntity.setExpiredAt(LocalDateTime.now().plusHours(2));
        tokenRepository.save(tokenEntity);
        return tokenEntity.getToken();
    }

    public void deleteToken(String token) {
        TokenEntity tokenEntity = tokenRepository.findByToken(token).orElseThrow(() ->
                new ResourceNotFoundException("Token não encontrado"));
        tokenRepository.delete(tokenEntity);
    }
}
