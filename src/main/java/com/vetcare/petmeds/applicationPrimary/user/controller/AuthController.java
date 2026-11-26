package com.vetcare.petmeds.applicationPrimary.user.controller;

import com.vetcare.petmeds.applicationPrimary.user.dto.LoginRequestDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.ResponseLoginDTO;
import com.vetcare.petmeds.applicationPrimary.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessions")
@AllArgsConstructor
@Tag(name = "Autenticação", description = "Criação (login) e encerramento (logout) da sessão do usuário")
public class AuthController implements AuthControllerInterface {

    private UserService userService;

    @Override
    public ResponseEntity<ResponseLoginDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(userService.login(loginRequestDTO.email(), loginRequestDTO.password()));
    }

    @Override
    public ResponseEntity<ResponseLoginDTO> logout(@RequestHeader("Authorization") String authHeader) {
        userService.logout(authHeader);
        return ResponseEntity.ok(new ResponseLoginDTO("Logout com Sucesso"));
    }
}
