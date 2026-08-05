package com.vetcare.petmeds.config;

import com.vetcare.petmeds.modules.token.repository.TokenRepository;
import com.vetcare.petmeds.modules.token.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenRepository tokenRepository;
    private final TokenService tokenService;

    public TokenAuthenticationFilter(TokenRepository tokenRepository, TokenService tokenService) {
        this.tokenRepository = tokenRepository;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        
        // Rotas públicas que não precisam de token
        if (path.startsWith("/user/login") || path.startsWith("/user/create")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token ausente ou formato inválido");
            return;
        }

        String token = authHeader.substring(7);

        var tokenEntityOpt = tokenRepository.findByToken(token);

        if (tokenEntityOpt.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido");
            return;
        }

        if (tokenService.isTokenExpired(tokenEntityOpt.get())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expirado");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
