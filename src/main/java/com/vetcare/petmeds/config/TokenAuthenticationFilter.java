package com.vetcare.petmeds.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetcare.petmeds.modules.token.service.TokenService;
import com.vetcare.petmeds.modules.user.repository.UserRepository;
import com.vetcare.petmeds.shared.ErrorDTO;
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
import java.util.Date;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public TokenAuthenticationFilter(TokenService tokenService, UserRepository userRepository, ObjectMapper objectMapper) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
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
            ErrorDTO errorDTO = new ErrorDTO(
                    new Date(),
                    "Token ausente ou formato inválido",
                    "Houve um erro ao chamar esta rota, o token não foi informado ou encontra-se inválido"
            );


            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            response.getWriter().write(objectMapper.writeValueAsString(errorDTO));
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
            ErrorDTO errorDTO = new ErrorDTO(
                    new Date(),
                    "Token ausente ou formato inválido",
                    "Houve um erro ao chamar esta rota, o token não foi informado ou encontra-se inválido"
            );


            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            response.getWriter().write(objectMapper.writeValueAsString(errorDTO));
            return;
        }

        filterChain.doFilter(request, response);
    }
}