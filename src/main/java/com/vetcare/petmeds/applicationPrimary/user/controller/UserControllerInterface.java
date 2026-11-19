package com.vetcare.petmeds.applicationPrimary.user.controller;

import com.vetcare.petmeds.applicationPrimary.user.dto.LoginRequestDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.ResponseLoginDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.UserDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.UserProfileDTO;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import com.vetcare.petmeds.archives.images.dto.ImageUserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

public interface UserControllerInterface {

    @GetMapping("/all")
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista paginada de usuários")
    ResponseEntity<Page<UserEntity>> getALl(@PageableDefault(page = 0, size = 10) Pageable pageable);

    @PostMapping
    @Operation(summary = "Cadastro de usuário", description = "Cadastra um novo usuário no sistema")
    ResponseEntity<ResponseLoginDTO> create(@RequestBody UserDTO user);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Busca os detalhes de um usuário pelo seu ID")
    ResponseEntity<UserProfileDTO> getUserById(@PathVariable Long id);

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuário por e-mail", description = "Busca um usuário pelo seu e-mail")
    ResponseEntity<UserEntity> getUserByEmail(@PathVariable String email);

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Realiza o login de um usuário")
    ResponseEntity<ResponseLoginDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO);

    @DeleteMapping("/logout")
    @Operation(summary = "Logout de usuário", description = "Realiza o logout do usuário atual")
    ResponseEntity<ResponseLoginDTO> logout(@RequestHeader("Authorization") String authHeader);

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    ResponseEntity<ResponseLoginDTO> update(@PathVariable Long id, @RequestBody UserEntity user);

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover usuário", description = "Remove um usuário do sistema")
    ResponseEntity<ResponseLoginDTO> delete(@PathVariable Long id);

    @PostMapping("/image/{id}")
    @Operation(summary = "Adiciona uma imagem do usuário", description = "Adiciona uma imagem ao usuário do sistema")
    ResponseEntity<ImageUserResponseDTO> uploadImageToUser(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image
    );

    @PatchMapping("/image/{id}")
    @Operation(summary = "Atualiza a imagem do usuário", description = "Atualiza a imagem do usuário no sistema")
    ResponseEntity<ImageUserResponseDTO> updateImageToUser(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image
    );

    @DeleteMapping("/image/{id}")
    @Operation(summary = "Remover imagem do usuário", description = "Remove a imagem do usuário do sistema")
    ResponseEntity<Void> deletedImageToUser(@PathVariable Long id);

    @GetMapping("/image/{id}")
    @Operation(summary = "Busca a imagem do usuário", description = "Busca a imagem do usuário no sistema")
    ResponseEntity<Resource> findByIdToImage(@PathVariable Long id);
}
