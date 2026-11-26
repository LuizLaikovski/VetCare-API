package com.vetcare.petmeds.model.token;

import com.vetcare.petmeds.applicationPrimary.token.service.TokenService;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class TokenServiceTest {

    private static final String CACHE_NAME = "active-tokens";

    private TokenService tokenService;
    private ConcurrentMapCacheManager cacheManager;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        cacheManager = new ConcurrentMapCacheManager();
        tokenService = new TokenService(cacheManager);
        ReflectionTestUtils.setField(tokenService, "secret", "my-very-secret-key-for-testing-purposes");

        user = new UserEntity();
        user.setId(1L);
        user.setEmail("john@example.com");
    }

    private Cache activeTokens() {
        return cacheManager.getCache(CACHE_NAME);
    }

    @Test
    void generateAndValidateToken_ShouldSucceed() {
        String token = tokenService.generateAndStoreToken(user);
        assertNotNull(token);
        // o @CachePut só atua dentro do contexto Spring; aqui registramos a sessão ativa manualmente
        activeTokens().put(user.getEmail(), token);

        assertEquals("john@example.com", tokenService.validateToken(token));
    }

    @Test
    void validateToken_ShouldReturnNullForInvalidToken() {
        assertNull(tokenService.validateToken("invalid.jwt.token"));
    }

    @Test
    void validateToken_ShouldReturnNullWhenTokenIsNotTheActiveOne() {
        String token = tokenService.generateAndStoreToken(user);
        activeTokens().put(user.getEmail(), "outro-token");

        assertNull(tokenService.validateToken(token));
    }

    @Test
    void validateToken_ShouldReturnNullWhenSessionWasNotStored() {
        String token = tokenService.generateAndStoreToken(user);

        assertNull(tokenService.validateToken(token));
    }

    @Test
    void validateToken_ShouldReturnNullForTokenSignedWithAnotherSecret() {
        String token = tokenService.generateAndStoreToken(user);
        TokenService other = new TokenService(cacheManager);
        ReflectionTestUtils.setField(other, "secret", "another-secret-key-with-enough-length");
        activeTokens().put(user.getEmail(), token);

        assertNull(other.validateToken(token));
    }

    @Test
    void isRevoked_ShouldReturnFalseByDefault() {
        assertFalse(tokenService.isRevoked("some-token"));
    }

    @Test
    void revokeToken_ShouldEvictActiveSession() {
        String token = tokenService.generateAndStoreToken(user);
        activeTokens().put(user.getEmail(), token);

        tokenService.revokeToken(token);

        assertNull(activeTokens().get(user.getEmail()));
        assertNull(tokenService.validateToken(token));
    }

    @Test
    void revokeToken_ShouldIgnoreInvalidToken() {
        assertDoesNotThrow(() -> tokenService.revokeToken("some-token"));
    }
}
