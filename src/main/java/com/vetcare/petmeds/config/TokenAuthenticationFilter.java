package com.vetcare.petmeds.config;

import com.vetcare.petmeds.modules.token.service.TokenService;
import com.vetcare.petmeds.modules.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public TokenAuthenticationFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println(">>> Requisição para: " + path); // LOG DEBUG

        // Rotas públicas que não precisam de token
        if (path.startsWith("/user/login") || path.startsWith("/user/create") || path.startsWith("/user/logout")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        System.out.println(">>> AuthHeader: " + authHeader); // LOG DEBUG

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println(">>> Token ausente ou formato inválido"); // LOG DEBUG
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token ausente ou formato inválido");
            return;
        }

        String token = authHeader.substring(7);
        String userEmail = tokenService.validateToken(token);
        System.out.println(">>> UserEmail extraído: " + userEmail); // LOG DEBUG

        if (userEmail != null) {
            userRepository.findByEmail(userEmail).ifPresent(user -> {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user, null, user.getAutorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println(">>> Contexto de segurança configurado para: " + userEmail); // LOG DEBUG
            });
        }
 else {
            System.out.println(">>> Token inválido ou expirado"); // LOG DEBUG
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido ou expirado");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
