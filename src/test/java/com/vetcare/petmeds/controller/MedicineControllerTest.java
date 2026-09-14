package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.modules.user.dto.ResponseLoginDTO;
import com.vetcare.petmeds.modules.medicine.controller.MedicineController;
import com.vetcare.petmeds.modules.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.modules.medicine.service.MedicineService;
import com.vetcare.petmeds.modules.medicine.entity.TypeMedicine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

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

    @BeforeEach
    void setUp() {
        medicine = new MedicineEntity();
        medicine.setId(1L);
        medicine.setName("Paracetamol");
        medicine.setType(TypeMedicine.pill);
    }

    @Test
    void getMedicineById_ShouldReturnOk() throws Exception {
        when(medicineService.getMedicineById(1L)).thenReturn(medicine);

        mockMvc.perform(get("/medicine/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Paracetamol"));
    }

    @Test
    void getAllMedicines_ShouldReturnOk() throws Exception {
        org.springframework.data.domain.Page<MedicineEntity> page = new org.springframework.data.domain.PageImpl<>(Arrays.asList(medicine));
        when(medicineService.getAllMedicines(any(org.springframework.data.domain.Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/medicine/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Paracetamol"));
    }

    @Test
    void createMedicine_ShouldReturnOk() throws Exception {
        when(medicineService.createMedicine(any(MedicineEntity.class))).thenReturn(medicine);

        mockMvc.perform(post("/medicine/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicine)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Paracetamol"));
    }

    @Test
    void updateMedicine_ShouldReturnOk() throws Exception {
        when(medicineService.updateMedicine(anyLong(), any(MedicineEntity.class))).thenReturn(medicine);

        mockMvc.perform(put("/medicine/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicine)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Paracetamol"));
    }

    @Test
    void deleteMedicine_ShouldReturnOk() throws Exception {
        when(medicineService.deleteMedicine(1L)).thenReturn(new ResponseLoginDTO("Medicine deleted successfully"));

        mockMvc.perform(delete("/medicine/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Medicine deleted successfully"));
    }
}
