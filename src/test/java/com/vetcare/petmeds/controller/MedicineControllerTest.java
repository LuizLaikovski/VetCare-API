package com.vetcare.petmeds.controller;

import com.vetcare.petmeds.dto.ResponseDTO;
import com.vetcare.petmeds.model.Medicine;
import com.vetcare.petmeds.service.MedicineService;
import com.vetcare.petmeds.utilities.TypeMedicine;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicineController.class)
public class MedicineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MedicineService medicineService;

    @Autowired
    private ObjectMapper objectMapper;

    private Medicine medicine;

    @BeforeEach
    void setUp() {
        medicine = new Medicine();
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
        when(medicineService.getAllMedicines()).thenReturn(Arrays.asList(medicine));

        mockMvc.perform(get("/medicine/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Paracetamol"));
    }

    @Test
    void createMedicine_ShouldReturnOk() throws Exception {
        when(medicineService.createMedicine(any(Medicine.class))).thenReturn(medicine);

        mockMvc.perform(post("/medicine/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicine)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Paracetamol"));
    }

    @Test
    void updateMedicine_ShouldReturnOk() throws Exception {
        when(medicineService.updateMedicine(anyLong(), any(Medicine.class))).thenReturn(medicine);

        mockMvc.perform(put("/medicine/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicine)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Paracetamol"));
    }

    @Test
    void deleteMedicine_ShouldReturnOk() throws Exception {
        when(medicineService.deleteMedicine(1L)).thenReturn(new ResponseDTO("Medicine deleted successfully"));

        mockMvc.perform(delete("/medicine/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Medicine deleted successfully"));
    }
}
