package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.dto.LoginRequestDTO;
import com.vetcare.petmeds.dto.ResponseDTO;
import com.vetcare.petmeds.model.user.UserEntity;
import com.vetcare.petmeds.model.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
    }

    @Test
    void getAllUsers_ShouldReturnOk() throws Exception {
        when(userService.findAll()).thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void createUser_ShouldReturnOk() throws Exception {
        when(userService.newUser(any(UserEntity.class))).thenReturn(new ResponseDTO("Usuario cadastrado com sucesso"));

        mockMvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Usuario cadastrado com sucesso"));
    }

    @Test
    void getUserById_ShouldReturnUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void login_ShouldReturnOk() throws Exception {
        LoginRequestDTO loginRequest = new LoginRequestDTO("john@example.com", "password123");
        when(userService.login(anyString(), anyString())).thenReturn(new ResponseDTO("Login realizado com sucesso!"));

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Login realizado com sucesso!"));
    }

    @Test
    void deleteUser_ShouldReturnOk() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(new ResponseDTO("O usuario foi removido com sucesso!"));

        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("O usuario foi removido com sucesso!"));
    }
}
