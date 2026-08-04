package com.vetcare.petmeds.modules.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO (
        @NotNull(message = "Erro: e-mail inválido")
        @NotBlank(message = "Erro: Enviado em branco")
        @Email
        String email,
        String password
) {}
