package com.vetcare.petmeds.model.medicine;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.applicationPrimary.medicine.dto.MedicineResponseDTO;
import com.vetcare.petmeds.applicationPrimary.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.applicationPrimary.medicine.repository.MedicineRepository;
import com.vetcare.petmeds.applicationPrimary.medicine.service.MedicineService;
import com.vetcare.petmeds.applicationPrimary.medicine.entity.TypeMedicine;
import com.vetcare.petmeds.shared.ResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicineServiceTest {

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private MedicineService medicineService;

    private MedicineEntity medicine;

    @BeforeEach
    void setUp() {
        medicine = new MedicineEntity();
        medicine.setId(1L);
        medicine.setName("Paracetamol");
        medicine.setManufacturer("PharmaCorp");
        medicine.setType(TypeMedicine.pill);
        medicine.setIndicatedSpecies("Dog, Cat");
        medicine.setDosage("500mg");
    }

    @Test
    void getMedicineById_ShouldReturnMedicineResponseWhenExists() {
        when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicine));

        MedicineResponseDTO result = medicineService.getMedicineById(1L);

        assertNotNull(result);
        assertEquals("Paracetamol", result.getName());
        verify(medicineRepository, times(1)).findById(1L);
    }

    @Test
    void getMedicineById_ShouldThrowExceptionWhenNotExists() {
        when(medicineRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> medicineService.getMedicineById(1L));
        verify(medicineRepository, times(1)).findById(1L);
    }

    @Test
    void getAllMedicines_ShouldReturnListOfMedicines() {
        List<MedicineEntity> medicines = Arrays.asList(medicine);
        Page<MedicineEntity> page = new PageImpl<>(medicines);
        when(medicineRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<MedicineResponseDTO> result = medicineService.getAllMedicines(Pageable.unpaged());

        assertEquals(1, result.getContent().size());
        assertEquals("Paracetamol", result.getContent().get(0).getName());
        verify(medicineRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getByName_ShouldReturnPageOfMedicinesMatchingName() {
        Page<MedicineEntity> page = new PageImpl<>(Arrays.asList(medicine));
        when(medicineRepository.findByNameContainingIgnoreCase(eq("para"), any(Pageable.class))).thenReturn(page);

        Page<MedicineResponseDTO> result = medicineService.getByName("para", Pageable.unpaged());

        assertEquals(1, result.getContent().size());
        assertEquals("Paracetamol", result.getContent().get(0).getName());
        verify(medicineRepository, times(1)).findByNameContainingIgnoreCase(eq("para"), any(Pageable.class));
    }

    @Test
    void createMedicine_ShouldReturnSavedMedicineResponse() {
        when(medicineRepository.save(any(MedicineEntity.class))).thenReturn(medicine);

        MedicineResponseDTO result = medicineService.createMedicine(medicine);

        assertNotNull(result);
        assertEquals("Paracetamol", result.getName());
        verify(medicineRepository, times(1)).save(medicine);
    }

    @Test
    void updateMedicine_ShouldReturnUpdatedMedicineResponse() {
        MedicineEntity updatedInfo = new MedicineEntity();
        updatedInfo.setName("Paracetamol Updated");
        updatedInfo.setManufacturer("PharmaCorp");
        updatedInfo.setType(TypeMedicine.pill);
        updatedInfo.setIndicatedSpecies("Dog");
        updatedInfo.setDosage("1000mg");

        when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicine));
        when(medicineRepository.save(any(MedicineEntity.class))).thenReturn(medicine);

        MedicineResponseDTO result = medicineService.updateMedicine(1L, updatedInfo);

        assertNotNull(result);
        verify(medicineRepository, times(1)).save(any(MedicineEntity.class));
    }

    @Test
    void deleteMedicine_ShouldReturnResponse() {
        when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicine));
        doNothing().when(medicineRepository).deleteById(1L);

        ResponseDTO response = medicineService.deleteMedicine(1L);

        assertNotNull(response);
        assertEquals("Deletado", response.getMessage());
        verify(medicineRepository, times(1)).deleteById(1L);
    }
}
