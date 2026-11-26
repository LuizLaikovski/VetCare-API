package com.vetcare.petmeds.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetcare.petmeds.applicationPrimary.token.service.TokenService;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import com.vetcare.petmeds.applicationPrimary.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/** Garante que apenas as rotas públicas (/sessions e Swagger) dispensam token; o cadastro de usuário exige token. */
@ExtendWith(MockitoExtension.class)
public class TokenAuthenticationFilterTest {

    @Mock
    private TokenService tokenService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FilterChain chain;

    private TokenAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        filter = new TokenAuthenticationFilter(tokenService, userRepository, new ObjectMapper());
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private MockHttpServletResponse run(String method, String uri, String authorization) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest(method, uri);
        if (authorization != null) request.addHeader("Authorization", authorization);
        MockHttpServletResponse response = new MockHttpServletResponse();
        filter.doFilter(request, response, chain);
        return response;
    }

    @Test
    void postUsers_ShouldRequireToken() throws Exception {
        MockHttpServletResponse response = run("POST", "/users", null);

        assertEquals(401, response.getStatus());
        verify(chain, never()).doFilter(any(), any());
    }

    @Test
    void postUsers_WithValidToken_ShouldBeAllowed() throws Exception {
        UserEntity admin = new UserEntity();
        admin.setEmail("admin@vetcare.com");
        when(tokenService.validateToken("abc")).thenReturn("admin@vetcare.com");
        when(userRepository.findByEmail("admin@vetcare.com")).thenReturn(Optional.of(admin));

        MockHttpServletResponse response = run("POST", "/users", "Bearer abc");

        assertEquals(200, response.getStatus());
        verify(chain).doFilter(any(), any());
    }

    @Test
    void postUsers_WithInvalidToken_ShouldReturn401() throws Exception {
        when(tokenService.validateToken("ruim")).thenReturn(null);

        assertEquals(401, run("POST", "/users", "Bearer ruim").getStatus());
        verify(chain, never()).doFilter(any(), any());
    }

    @Test
    void getUsers_ShouldRequireToken() throws Exception {
        MockHttpServletResponse response = run("GET", "/users", null);

        assertEquals(401, response.getStatus());
        verify(chain, never()).doFilter(any(), any());
    }

    @Test
    void deleteUsers_ShouldRequireToken() throws Exception {
        assertEquals(401, run("DELETE", "/users", null).getStatus());
    }

    @Test
    void sessions_ShouldBePublicForLoginAndLogout() throws Exception {
        assertEquals(200, run("POST", "/sessions", null).getStatus());
        assertEquals(200, run("DELETE", "/sessions", null).getStatus());
        verify(chain, times(2)).doFilter(any(), any());
    }

    @Test
    void swagger_ShouldBePublic() throws Exception {
        assertEquals(200, run("GET", "/swagger-ui/index.html", null).getStatus());
        assertEquals(200, run("GET", "/v3/api-docs", null).getStatus());
    }

    @Test
    void oldAuthRoutes_ShouldNotBePublicAnymore() throws Exception {
        assertEquals(401, run("POST", "/user/login", null).getStatus());
        assertEquals(401, run("DELETE", "/user/logout", null).getStatus());
    }

    @Test
    void protectedRoute_WithoutBearerPrefix_ShouldReturn401() throws Exception {
        MockHttpServletResponse response = run("GET", "/animals", "token-sem-prefixo");

        assertEquals(401, response.getStatus());
        assertTrue(response.getContentAsString().contains("Token ausente"));
    }

    @Test
    void protectedRoute_WithValidToken_ShouldAuthenticate() throws Exception {
        UserEntity user = new UserEntity();
        user.setEmail("john@example.com");
        when(tokenService.validateToken("abc")).thenReturn("john@example.com");
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        run("GET", "/animals", "Bearer abc");

        verify(chain).doFilter(any(), any());
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(user, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Test
    void protectedRoute_WithInvalidToken_ShouldNotAuthenticate() throws Exception {
        when(tokenService.validateToken("ruim")).thenReturn(null);

        MockHttpServletResponse response = run("GET", "/animals", "Bearer ruim");

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(401, response.getStatus());
        verify(chain, never()).doFilter(any(), any());
    }
}
