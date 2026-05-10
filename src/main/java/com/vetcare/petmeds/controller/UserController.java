package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.dto.LoginRequestDTO;
import com.vetcare.petmeds.dto.ResponseDTO;
import com.vetcare.petmeds.model.User;
import com.vetcare.petmeds.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/all")
    public List<User> getALl() { return userService.findAll(); }

    @PostMapping("/create")
    public ResponseDTO create(@RequestBody User user) {
        userService.newUser(user);
        return new ResponseDTO("Usuario cadastrado com sucesso");
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getEmail")
    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return userService.login(loginRequestDTO.email(), loginRequestDTO.password());
    }

    @PutMapping("/update")
    public ResponseDTO update(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO delete(@PathVariable Long id) {
        return userService.deleteUser(id);
    }


}
