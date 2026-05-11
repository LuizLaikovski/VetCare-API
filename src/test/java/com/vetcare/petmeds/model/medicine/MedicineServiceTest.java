package com.vetcare.petmeds.model.medicine;

import com.vetcare.petmeds.dto.ResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    void getMedicineById_ShouldReturnMedicineWhenExists() {
        when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicine));

        MedicineEntity result = medicineService.getMedicineById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(medicineRepository, times(1)).findById(1L);
    }

    @Test
    void getMedicineById_ShouldReturnNullWhenNotExists() {
        when(medicineRepository.findById(1L)).thenReturn(Optional.empty());

        MedicineEntity result = medicineService.getMedicineById(1L);

        assertNull(result);
        verify(medicineRepository, times(1)).findById(1L);
    }

    @Test
    void getAllMedicines_ShouldReturnListOfMedicines() {
        List<MedicineEntity> medicines = Arrays.asList(medicine);
        when(medicineRepository.findAll()).thenReturn(medicines);

        List<MedicineEntity> result = medicineService.getAllMedicines();

        assertEquals(1, result.size());
        verify(medicineRepository, times(1)).findAll();
    }

    @Test
    void getByName_ShouldReturnListOfMedicinesMatchingName() {
        List<MedicineEntity> medicines = Arrays.asList(medicine);
        when(medicineRepository.findByNameContainingIgnoreCase("para")).thenReturn(medicines);

        List<MedicineEntity> result = medicineService.getByName("para");

        assertEquals(1, result.size());
        assertEquals("Paracetamol", result.get(0).getName());
        verify(medicineRepository, times(1)).findByNameContainingIgnoreCase("para");
    }

    @Test
    void createMedicine_ShouldReturnSavedMedicine() {
        when(medicineRepository.save(any(MedicineEntity.class))).thenReturn(medicine);

        MedicineEntity result = medicineService.createMedicine(medicine);

        assertNotNull(result);
        assertEquals("Paracetamol", result.getName());
        verify(medicineRepository, times(1)).save(medicine);
    }

    @Test
    void createAllsMedicine_ShouldReturnSuccessResponse() {
        List<MedicineEntity> medicines = Arrays.asList(medicine, new MedicineEntity());
        when(medicineRepository.save(any(MedicineEntity.class))).thenReturn(medicine);

        ResponseDTO response = medicineService.createAllsMedicine(medicines);

        assertTrue(response.getResponse().contains("medicamentos foram criados com sucesso"));
        verify(medicineRepository, times(2)).save(any(MedicineEntity.class));
    }

    @Test
    void updateMedicine_ShouldReturnUpdatedMedicine() {
        MedicineEntity updatedInfo = new MedicineEntity();
        updatedInfo.setName("Paracetamol Updated");

        when(medicineRepository.findById(1L)).thenReturn(Optional.of(medicine));
        when(medicineRepository.save(any(MedicineEntity.class))).thenReturn(medicine);

        MedicineEntity result = medicineService.updateMedicine(1L, updatedInfo);

        assertNotNull(result);
        assertEquals("Paracetamol Updated", result.getName());
        verify(medicineRepository, times(1)).save(medicine);
    }

    @Test
    void deleteMedicine_ShouldReturnSuccessResponse() {
        doNothing().when(medicineRepository).deleteById(1L);

        ResponseDTO response = medicineService.deleteMedicine(1L);

        assertEquals("Medicine deleted successfully", response.getResponse());
        verify(medicineRepository, times(1)).deleteById(1L);
    }
}
