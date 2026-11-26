package com.vetcare.petmeds.applicationPrimary.user.controller;

import com.vetcare.petmeds.applicationPrimary.user.dto.LoginRequestDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.ResponseLoginDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.UserDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.UserProfileDTO;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import com.vetcare.petmeds.applicationPrimary.user.service.UserService;
import com.vetcare.petmeds.archives.images.dto.ImageUserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Tag(name = "Usuario", description = "Gerenciamento de rotas para o usuario")
public class UserController implements UserControllerInterface {

    private UserService userService;

    @Override
    public ResponseEntity<Page<UserEntity>> getALl(String email, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        if (email != null && !email.isBlank()) {
            UserEntity user = userService.getUserByEmail(email);
            return ResponseEntity.ok(new PageImpl<>(List.of(user), pageable, 1));
        }
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @Override
    public ResponseEntity<ResponseLoginDTO> create(@RequestBody UserDTO user) {
        userService.newUser(user);
        return new ResponseEntity<>(new ResponseLoginDTO("Usuario cadastrado com sucesso"), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserProfileDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserByIdToDTO(id));
    }

    @Override
    public ResponseEntity<ResponseLoginDTO> update(@PathVariable Long id, @RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @Override
    public ResponseEntity<ResponseLoginDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @Override
    public ResponseEntity<ImageUserResponseDTO> uploadImageToUser(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image
    ) {
        return new ResponseEntity<>(userService.uploadImageToUser(id, image), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ImageUserResponseDTO> updateImageToUser(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image
    ) {
        return ResponseEntity.ok(userService.updateImageToUser(id, image));
    }

    @Override
    public ResponseEntity<Void> deletedImageToUser(@PathVariable Long id) {
        userService.deletedImageToUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Resource> findByIdToImage(@PathVariable Long id) {
        return userService.getByImage(id);
    }

}
