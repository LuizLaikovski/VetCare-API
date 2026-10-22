package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.modules.vaccine.controller.VaccineController;
import com.vetcare.petmeds.modules.vaccine.entity.VaccineEntity;
import com.vetcare.petmeds.modules.vaccine.service.VaccineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VaccineController.class, 
    excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {com.vetcare.petmeds.config.SecurityConfig.class, com.vetcare.petmeds.config.TokenAuthenticationFilter.class}),
    excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class VaccineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VaccineService vaccineService;

    @Autowired
    private ObjectMapper objectMapper;

    private VaccineEntity vaccine;

    @BeforeEach
    void setUp() {
        vaccine = new VaccineEntity();
        vaccine.setId(1L);
        vaccine.setVaccineName("Raiva");
        vaccine.setDescription("Vacina contra raiva");
        vaccine.setNumOfDoses(1);
        vaccine.setDayInterval(365);
    }

    @Test
    void getVaccineById_ShouldReturnOk() throws Exception {
        when(vaccineService.getById(1L)).thenReturn(vaccine);

        mockMvc.perform(get("/vaccine/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vaccineName").value("Raiva"));
    }

    @Test
    void getVaccineByName_ShouldReturnOk() throws Exception {
        org.springframework.data.domain.Page<VaccineEntity> page = new org.springframework.data.domain.PageImpl<>(Arrays.asList(vaccine));
        when(vaccineService.getByName(org.mockito.ArgumentMatchers.eq("Raiva"), any(org.springframework.data.domain.Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/vaccine/name/Raiva"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].vaccineName").value("Raiva"));
    }

    @Test
    void getAllVaccines_ShouldReturnOk() throws Exception {
        org.springframework.data.domain.Page<VaccineEntity> page = new org.springframework.data.domain.PageImpl<>(Arrays.asList(vaccine));
        when(vaccineService.getAll(any(org.springframework.data.domain.Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/vaccine/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].vaccineName").value("Raiva"));
    }

    @Test
    void createVaccine_ShouldReturnCreated() throws Exception {
        when(vaccineService.createVaccine(any(VaccineEntity.class))).thenReturn(vaccine);

        mockMvc.perform(post("/vaccine/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vaccine)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vaccineName").value("Raiva"));
    }

    @Test
    void updateVaccine_ShouldReturnOk() throws Exception {
        when(vaccineService.updateVaccine(any(VaccineEntity.class))).thenReturn(vaccine);

        mockMvc.perform(put("/vaccine/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vaccine)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vaccineName").value("Raiva"));
    }

    @Test
    void deleteVaccine_ShouldReturnNoContent() throws Exception {
        doNothing().when(vaccineService).delete(1L);

        mockMvc.perform(delete("/vaccine/1"))
                .andExpect(status().isNoContent());
    }
}
