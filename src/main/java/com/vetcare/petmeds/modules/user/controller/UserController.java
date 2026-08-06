package com.vetcare.petmeds.modules.user.controller;

import com.vetcare.petmeds.modules.user.dto.LoginRequestDTO;
import com.vetcare.petmeds.modules.user.dto.LogoutRequestDTO;
import com.vetcare.petmeds.modules.user.dto.ResponseDTO;
import com.vetcare.petmeds.modules.user.dto.UserDTO;
import com.vetcare.petmeds.modules.user.entity.UserEntity;
import com.vetcare.petmeds.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/all")
    public List<UserEntity> getALl() { return userService.findAll();  }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody UserDTO user) {
        userService.newUser(user);
        return new ResponseDTO("Usuario cadastrado com sucesso");
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getEmail")
    public UserEntity getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/login")
    public ResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return userService.login(loginRequestDTO.email(), loginRequestDTO.password());
    }

    @DeleteMapping("/logout")
    public ResponseDTO logout(@RequestHeader("Authorization") String authHeader) {
        userService.logout(authHeader);
        return new ResponseDTO("Logout com Sucesso");
    }

    @PutMapping("/update/{id}")
    public ResponseDTO update(@PathVariable Long id, @RequestBody UserEntity user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO delete(@PathVariable Long id) {
        return userService.deleteUser(id);
    }


}
