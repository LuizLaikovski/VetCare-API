package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.applicationPrimary.medicine.controller.MedicineController;
import com.vetcare.petmeds.applicationPrimary.medicine.dto.MedicineResponseDTO;
import com.vetcare.petmeds.applicationPrimary.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.applicationPrimary.medicine.service.MedicineService;
import com.vetcare.petmeds.applicationPrimary.medicine.entity.TypeMedicine;
import com.vetcare.petmeds.shared.ResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MedicineController.class, 
    excludeFilters = @org.springframework.context.annotation.ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {com.vetcare.petmeds.config.SecurityConfig.class, com.vetcare.petmeds.config.TokenAuthenticationFilter.class}),
    excludeAutoConfiguration = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class MedicineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MedicineService medicineService;

    @Autowired
    private ObjectMapper objectMapper;

    private MedicineEntity medicine;
    private MedicineResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        medicine = new MedicineEntity();
        medicine.setId(1L);
        medicine.setName("Paracetamol");
        medicine.setType(TypeMedicine.pill);

        responseDTO = new MedicineResponseDTO(medicine);
    }

    @Test
    void getMedicineById_ShouldReturnOk() throws Exception {
        when(medicineService.getMedicineById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/medicine/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Paracetamol"));
    }

    @Test
    void getAllMedicines_ShouldReturnOk() throws Exception {
        var page = new PageImpl<>(Arrays.asList(responseDTO));
        when(medicineService.getAllMedicines(any(org.springframework.data.domain.Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/medicine/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Paracetamol"));
    }

    @Test
    void createMedicine_ShouldReturnCreated() throws Exception {
        when(medicineService.createMedicine(any(MedicineEntity.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/medicine/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicine)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Paracetamol"));
    }

    @Test
    void updateMedicine_ShouldReturnOk() throws Exception {
        when(medicineService.updateMedicine(anyLong(), any(MedicineEntity.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/medicine/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicine)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Paracetamol"));
    }

    @Test
    void deleteMedicine_ShouldReturnNoContent() throws Exception {
        ResponseDTO res = new ResponseDTO(new Date(), "Deletado", "Medicamento deleteado com sucesso");
        when(medicineService.deleteMedicine(1L)).thenReturn(res);

        mockMvc.perform(delete("/medicine/1"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.message").value("Deletado"));
    }
}
