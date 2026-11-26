package com.vetcare.petmeds.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.controller.VaccinationRecordController;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.dto.RequestEditVaccinationRecord;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.dto.VaccinationRecordCreateDTO;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.dto.VaccinationRecordResponseDTO;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.service.VaccinationRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = VaccinationRecordController.class, 
    excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {com.vetcare.petmeds.config.SecurityConfig.class, com.vetcare.petmeds.config.TokenAuthenticationFilter.class}),
    excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class VaccinationRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VaccinationRecordService vaccinationRecordService;

    @Autowired
    private ObjectMapper objectMapper;

    private VaccinationRecordCreateDTO createDTO;
    private VaccinationRecordResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        createDTO = new VaccinationRecordCreateDTO();
        createDTO.setAnimalId(1L);
        createDTO.setVaccineId(1L);
        createDTO.setMedicId(1L);

        responseDTO = VaccinationRecordResponseDTO.builder()
                .id(1L)
                .applicationDate(new Date())
                .numOfDoses(1)
                .applicationStatus("APPLIED")
                .build();
    }

    @Test
    void create_ShouldReturnCreated() throws Exception {
        when(vaccinationRecordService.createVaccinationRecord(any(VaccinationRecordCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/vaccination-records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Registro de Vacina cadastrado com sucesso"));
    }

    @Test
    void findById_ShouldReturnOk() throws Exception {
        when(vaccinationRecordService.findById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/vaccination-records/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getAllVaccinationRecord_ShouldReturnOk() throws Exception {
        var page = new PageImpl<>(Arrays.asList(responseDTO));
        when(vaccinationRecordService.findAll(any())).thenReturn(page);

        mockMvc.perform(get("/vaccination-records"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void getVaccinationRecordByIdAnimal_ShouldReturnOk() throws Exception {
        var page = new PageImpl<>(Arrays.asList(responseDTO));
        when(vaccinationRecordService.findByAnimalId(anyLong(), any())).thenReturn(page);

        mockMvc.perform(get("/vaccination-records").param("animalId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void getVaccinationRecordByIdMedic_ShouldReturnOk() throws Exception {
        var page = new PageImpl<>(Arrays.asList(responseDTO));
        when(vaccinationRecordService.findByMedicId(anyLong(), any())).thenReturn(page);

        mockMvc.perform(get("/vaccination-records").param("medicId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void getVaccinationRecordByIdVaccine_ShouldReturnOk() throws Exception {
        var page = new PageImpl<>(Arrays.asList(responseDTO));
        when(vaccinationRecordService.findByVaccineId(anyLong(), any())).thenReturn(page);

        mockMvc.perform(get("/vaccination-records").param("vaccineId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void getVaccinationRecordByDate_ShouldReturnOk() throws Exception {
        var page = new PageImpl<>(Arrays.asList(responseDTO));
        when(vaccinationRecordService.findByApplicationDate(any(Date.class), any())).thenReturn(page);

        mockMvc.perform(get("/vaccination-records")
                        .param("date", "2026-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void getVaccinationRecordByBetweenDate_ShouldReturnOk() throws Exception {
        var page = new PageImpl<>(Arrays.asList(responseDTO));
        when(vaccinationRecordService.findByDateRange(any(Date.class), any(Date.class), any())).thenReturn(page);

        mockMvc.perform(get("/vaccination-records")
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void getVaccinationRecordByStatus_ShouldReturnOk() throws Exception {
        var page = new PageImpl<>(Arrays.asList(responseDTO));
        when(vaccinationRecordService.findByApplicationStatus(anyString(), any())).thenReturn(page);

        mockMvc.perform(get("/vaccination-records")
                        .param("status", "APPLIED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L));
    }

    @Test
    void edit_ShouldReturnOk() throws Exception {
        when(vaccinationRecordService.editVaccinationRecord(any(RequestEditVaccinationRecord.class), anyLong())).thenReturn(responseDTO);

        RequestEditVaccinationRecord editRequest = new RequestEditVaccinationRecord();
        editRequest.setApplicationDate(new Date());
        editRequest.setNumOfDoses(1);
        editRequest.setObservation("Test");

        mockMvc.perform(put("/vaccination-records/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
