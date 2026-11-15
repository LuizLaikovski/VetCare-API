package com.vetcare.petmeds.model.vaccine;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.applicationPrimary.animal.entity.Specie;
import com.vetcare.petmeds.applicationPrimary.vaccine.entity.VaccineEntity;
import com.vetcare.petmeds.applicationPrimary.vaccine.repository.VaccineRepository;
import com.vetcare.petmeds.applicationPrimary.vaccine.service.VaccineService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VaccineServiceTest {

    @Mock
    private VaccineRepository vaccineRepository;

    @InjectMocks
    private VaccineService vaccineService;

    private VaccineEntity vaccine;

    @BeforeEach
    void setUp() {
        vaccine = new VaccineEntity();
        vaccine.setId(1L);
        vaccine.setVaccineName("Raiva");
        vaccine.setDescription("Vacina contra raiva");
        vaccine.setNumOfDoses(1);
        vaccine.setDayInterval(365);
        vaccine.setSpecie(Specie.Cachorro);
    }

    @Test
    void getAll_ShouldReturnPageOfVaccines() {
        Page<VaccineEntity> page = new PageImpl<>(Arrays.asList(vaccine));
        when(vaccineRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<VaccineEntity> result = vaccineService.getAll(Pageable.unpaged());

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(vaccineRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getById_ShouldReturnVaccineWhenExists() {
        when(vaccineRepository.findById(1L)).thenReturn(Optional.of(vaccine));

        VaccineEntity result = vaccineService.getById(1L);

        assertNotNull(result);
        assertEquals("Raiva", result.getVaccineName());
        verify(vaccineRepository, times(1)).findById(1L);
    }

    @Test
    void getById_ShouldThrowExceptionWhenNotFound() {
        when(vaccineRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vaccineService.getById(1L));
        verify(vaccineRepository, times(1)).findById(1L);
    }

    @Test
    void getByName_ShouldReturnPageOfVaccinesMatchingName() {
        Page<VaccineEntity> page = new PageImpl<>(Arrays.asList(vaccine));
        when(vaccineRepository.findByVaccineNameContainingIgnoreCase(eq("Raiva"), any(Pageable.class))).thenReturn(page);

        Page<VaccineEntity> result = vaccineService.getByName("Raiva", Pageable.unpaged());

        assertFalse(result.isEmpty());
        assertEquals("Raiva", result.getContent().get(0).getVaccineName());
        verify(vaccineRepository, times(1)).findByVaccineNameContainingIgnoreCase(eq("Raiva"), any(Pageable.class));
    }

    @Test
    void createVaccine_ShouldReturnSavedVaccine() {
        when(vaccineRepository.save(any(VaccineEntity.class))).thenReturn(vaccine);

        VaccineEntity result = vaccineService.createVaccine(vaccine);

        assertNotNull(result);
        assertEquals("Raiva", result.getVaccineName());
        verify(vaccineRepository, times(1)).save(vaccine);
    }

    @Test
    void updateVaccine_ShouldReturnUpdatedVaccine() {
        VaccineEntity updatedInfo = new VaccineEntity();
        updatedInfo.setId(1L);
        updatedInfo.setVaccineName("Raiva Updated");
        updatedInfo.setDescription("Nova descrição");
        updatedInfo.setNumOfDoses(2);

        when(vaccineRepository.findById(1L)).thenReturn(Optional.of(vaccine));
        when(vaccineRepository.save(any(VaccineEntity.class))).thenReturn(vaccine);

        VaccineEntity result = vaccineService.updateVaccine(updatedInfo);

        assertNotNull(result);
        assertEquals("Raiva Updated", result.getVaccineName());
        assertEquals(2, result.getNumOfDoses());
        verify(vaccineRepository, times(1)).save(vaccine);
    }

    @Test
    void updateVaccine_ShouldThrowExceptionWhenNotFound() {
        when(vaccineRepository.findById(1L)).thenReturn(Optional.empty());

        VaccineEntity updatedInfo = new VaccineEntity();
        updatedInfo.setId(1L);

        assertThrows(ResourceNotFoundException.class, () -> vaccineService.updateVaccine(updatedInfo));
        verify(vaccineRepository, never()).save(any());
    }

    @Test
    void delete_ShouldDeleteWhenExists() {
        when(vaccineRepository.existsById(1L)).thenReturn(true);
        doNothing().when(vaccineRepository).deleteById(1L);

        assertDoesNotThrow(() -> vaccineService.delete(1L));
        verify(vaccineRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_ShouldThrowExceptionWhenNotFound() {
        when(vaccineRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> vaccineService.delete(1L));
        verify(vaccineRepository, never()).deleteById(anyLong());
    }
}
