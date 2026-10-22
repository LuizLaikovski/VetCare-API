package com.vetcare.petmeds.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetcare.petmeds.modules.initialCheckUp.controller.InitialCheckUpController;
import com.vetcare.petmeds.modules.initialCheckUp.dto.InitialCheckUpDTO;
import com.vetcare.petmeds.modules.initialCheckUp.dto.InitialCheckUpResponseDTO;
import com.vetcare.petmeds.modules.initialCheckUp.service.InitialCheckUpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InitialCheckUpController.class, 
    excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {com.vetcare.petmeds.config.SecurityConfig.class, com.vetcare.petmeds.config.TokenAuthenticationFilter.class}),
    excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class InitialCheckUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InitialCheckUpService initialCheckUpService;

    @Autowired
    private ObjectMapper objectMapper;

    private InitialCheckUpDTO checkUpDTO;
    private InitialCheckUpResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        checkUpDTO = new InitialCheckUpDTO();
        checkUpDTO.setAnimalId(1L);
        checkUpDTO.setVeterinarianId(1L);

        responseDTO = InitialCheckUpResponseDTO.builder()
                .id(1L)
                .examinationDate(LocalDateTime.now())
                .build();
    }

    @Test
    void createCheckUp_ShouldReturnCreated() throws Exception {
        when(initialCheckUpService.create(any(InitialCheckUpDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/initialCheckUp/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(checkUpDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void updateCheckUp_ShouldReturnOk() throws Exception {
        when(initialCheckUpService.update(anyLong(), any(InitialCheckUpDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/initialCheckUp/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(checkUpDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void findAllCheckUps_ShouldReturnOk() throws Exception {
        var page = new PageImpl<>(Arrays.asList(responseDTO));
        when(initialCheckUpService.findAll(any())).thenReturn(page);

        mockMvc.perform(get("/initialCheckUp/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void findById_ShouldReturnOk() throws Exception {
        when(initialCheckUpService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/initialCheckUp/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void findByAnimalId_ShouldReturnOk() throws Exception {
        var page = new PageImpl<>(Arrays.asList(responseDTO));
        when(initialCheckUpService.findByAnimalId(anyLong(), any())).thenReturn(page);

        mockMvc.perform(get("/initialCheckUp/animal/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void findByVeterinarianId_ShouldReturnOk() throws Exception {
        var page = new PageImpl<>(Arrays.asList(responseDTO));
        when(initialCheckUpService.findByVeterinarianId(anyLong(), any())).thenReturn(page);

        mockMvc.perform(get("/initialCheckUp/vet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void findByDateRange_ShouldReturnOk() throws Exception {
        var page = new PageImpl<>(Arrays.asList(responseDTO));
        when(initialCheckUpService.findByDateRange(any(), any(), any())).thenReturn(page);

        mockMvc.perform(get("/initialCheckUp/date-range")
                        .param("start", "2026-01-01")
                        .param("end", "2026-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void deleteCheckUp_ShouldReturnOk() throws Exception {
        doNothing().when(initialCheckUpService).delete(1L);

        mockMvc.perform(delete("/initialCheckUp/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Checkup deletado com sucesso"));
    }
}
