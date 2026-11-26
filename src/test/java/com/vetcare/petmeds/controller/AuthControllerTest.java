package com.vetcare.petmeds.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetcare.petmeds.applicationPrimary.user.controller.AuthController;
import com.vetcare.petmeds.applicationPrimary.user.dto.LoginRequestDTO;
import com.vetcare.petmeds.applicationPrimary.user.dto.ResponseLoginDTO;
import com.vetcare.petmeds.applicationPrimary.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class,
    excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {com.vetcare.petmeds.config.SecurityConfig.class, com.vetcare.petmeds.config.TokenAuthenticationFilter.class}),
    excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_ShouldReturnOk() throws Exception {
        LoginRequestDTO loginRequest = new LoginRequestDTO("john@example.com", "password123");
        when(userService.login(anyString(), anyString())).thenReturn(new ResponseLoginDTO("Login realizado com sucesso!"));

        mockMvc.perform(post("/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Login realizado com sucesso!"));
    }

    @Test
    void login_WithInvalidBody_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void logout_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/sessions")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Logout com Sucesso"));

        verify(userService).logout("Bearer token");
    }

    @Test
    void logout_WithoutHeader_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(delete("/sessions"))
                .andExpect(status().isBadRequest());
    }
}
