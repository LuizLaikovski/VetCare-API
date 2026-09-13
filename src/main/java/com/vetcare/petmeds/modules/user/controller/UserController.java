package com.vetcare.petmeds.modules.user.controller;

import com.vetcare.petmeds.modules.user.dto.LoginRequestDTO;
import com.vetcare.petmeds.modules.user.dto.ResponseLoginDTO;
import com.vetcare.petmeds.modules.user.dto.UserDTO;
import com.vetcare.petmeds.modules.user.entity.UserEntity;
import com.vetcare.petmeds.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Tag(name = "Usuario", description = "Gerenciamento de rotas para o usuario")
public class UserController {

    private UserService userService;

    @GetMapping("/all")
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista paginada de usuários")
    public ResponseEntity<Page<UserEntity>> getALl(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @PostMapping("/create")
    @Operation(summary = "Cadastro de usuário", description = "Cadastra um novo usuário no sistema")
    public ResponseEntity<ResponseLoginDTO> create(@RequestBody UserDTO user) {
        userService.newUser(user);
        return new ResponseEntity<>(new ResponseLoginDTO("Usuario cadastrado com sucesso"), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Busca os detalhes de um usuário pelo seu ID")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuário por e-mail", description = "Busca um usuário pelo seu e-mail")
    public ResponseEntity<UserEntity> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Realiza o login de um usuário")
    public ResponseEntity<ResponseLoginDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(userService.login(loginRequestDTO.email(), loginRequestDTO.password()));
    }

    @DeleteMapping("/logout")
    @Operation(summary = "Logout de usuário", description = "Realiza o logout do usuário atual")
    public ResponseEntity<ResponseLoginDTO> logout(@RequestHeader("Authorization") String authHeader) {
        userService.logout(authHeader);
        return ResponseEntity.ok(new ResponseLoginDTO("Logout com Sucesso"));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    public ResponseEntity<ResponseLoginDTO> update(@PathVariable Long id, @RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover usuário", description = "Remove um usuário do sistema")
    public ResponseEntity<ResponseLoginDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
