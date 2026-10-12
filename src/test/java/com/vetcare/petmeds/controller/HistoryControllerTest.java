package com.vetcare.petmeds.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetcare.petmeds.modules.history.controller.HistoryController;
import com.vetcare.petmeds.modules.history.dto.HistoryRequestDTO;
import com.vetcare.petmeds.modules.history.dto.HistoryResponseDTO;
import com.vetcare.petmeds.modules.history.service.HistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HistoryController.class,
    excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {com.vetcare.petmeds.config.SecurityConfig.class, com.vetcare.petmeds.config.TokenAuthenticationFilter.class}),
    excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class HistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HistoryService historyService;

    @Autowired
    private ObjectMapper objectMapper;

    private HistoryRequestDTO requestDTO;
    private HistoryResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new HistoryRequestDTO(1L, 1L);
        responseDTO = HistoryResponseDTO.builder()
                .id(1L)
                .idAnimal(1L)
                .nameAnimal("Rex")
                .idCheckup(1L)
                .build();
    }

    @Test
    void createHistory_ShouldReturnCreated() throws Exception {
        when(historyService.create(any(HistoryRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/history/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nameAnimal").value("Rex"));
    }

    @Test
    void updateHistory_ShouldReturnOk() throws Exception {
        when(historyService.update(anyLong(), any(HistoryRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/history/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void findAllHistories_ShouldReturnOk() throws Exception {
        var page = new PageImpl<>(Arrays.asList(responseDTO));
        when(historyService.findAll(any())).thenReturn(page);

        mockMvc.perform(get("/history/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void findById_ShouldReturnOk() throws Exception {
        when(historyService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/history/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deleteHistory_ShouldReturnOk() throws Exception {
        doNothing().when(historyService).delete(1L);

        mockMvc.perform(delete("/history/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Histórico deletado com sucesso"));
    }
}
