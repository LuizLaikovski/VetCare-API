package com.vetcare.petmeds.model.token;

import com.vetcare.petmeds.applicationPrimary.token.service.TokenService;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenService, "secret", "my-very-secret-key-for-testing-purposes");

        user = new UserEntity();
        user.setId(1L);
        user.setEmail("john@example.com");
    }

    @Test
    void generateAndValidateToken_ShouldSucceed() {
        String token = tokenService.generateAndStoreToken(user);
        assertNotNull(token);

        String subject = tokenService.validateToken(token);
        assertEquals("john@example.com", subject);
    }

    @Test
    void validateToken_ShouldReturnNullForInvalidToken() {
        String invalidToken = "invalid.jwt.token";
        String subject = tokenService.validateToken(invalidToken);
        assertNull(subject);
    }

    @Test
    void isRevoked_ShouldReturnFalseByDefault() {
        assertFalse(tokenService.isRevoked("some-token"));
    }

    @Test
    void revokeToken_ShouldExecuteWithoutError() {
        assertDoesNotThrow(() -> tokenService.revokeToken("some-token"));
    }
}
