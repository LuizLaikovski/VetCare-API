package com.vetcare.petmeds.model.animal;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.applicationPrimary.animal.entity.AnimalEntity;
import com.vetcare.petmeds.applicationPrimary.animal.repository.AnimalRepository;
import com.vetcare.petmeds.applicationPrimary.animal.service.AnimalService;
import com.vetcare.petmeds.applicationPrimary.animal.entity.Specie;
import com.vetcare.petmeds.applicationPrimary.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.applicationPrimary.medicine.service.MedicineService;
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
public class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private MedicineService medicineService;

    @InjectMocks
    private AnimalService animalService;

    private AnimalEntity animal;

    @BeforeEach
    void setUp() {
        animal = new AnimalEntity();
        animal.setId(1L);
        animal.setName("Rex");
        animal.setSpecie(Specie.Cachorro);
        animal.setRace("Golden Retriever");
        animal.setAge(5);
        animal.setWeight(30.0);
    }

    @Test
    void getAll_ShouldReturnListOfAnimals() {
        List<AnimalEntity> animals = Arrays.asList(animal);
        Page<AnimalEntity> page = new PageImpl<>(animals);
        when(animalRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<AnimalEntity> result = animalService.getAll(Pageable.unpaged());

        assertEquals(1, result.getContent().size());
        assertEquals("Rex", result.getContent().get(0).getName());
        verify(animalRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getById_ShouldReturnAnimalWhenExists() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        AnimalEntity result = animalService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(animalRepository, times(1)).findById(1L);
    }

    @Test
    void getById_ShouldThrowExceptionWhenNotFound() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> animalService.getById(1L)
        );

        assertEquals("Animal não encontrado", exception.getMessage());
    }

    @Test
    void getByName_ShouldReturnPageOfAnimalsMatchingName() {
        Page<AnimalEntity> page = new PageImpl<>(Arrays.asList(animal));
        when(animalRepository.findByNameContainingIgnoreCase(eq("Rex"), any(Pageable.class))).thenReturn(page);

        Page<AnimalEntity> result = animalService.getByName("Rex", Pageable.unpaged());

        assertFalse(result.isEmpty());
        assertEquals("Rex", result.getContent().get(0).getName());
        verify(animalRepository, times(1)).findByNameContainingIgnoreCase(eq("Rex"), any(Pageable.class));
    }

    @Test
    void setMedicine_ShouldAddMedicineAndReturnResponse() {
        MedicineEntity medicine = new MedicineEntity();
        medicine.setId(10L);
        medicine.setName("Vitamina C");

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(medicineService.getMedicineByIdEntity(10L)).thenReturn(medicine);
        when(animalRepository.save(any(AnimalEntity.class))).thenReturn(animal);

        ResponseDTO response = animalService.setMedicine(1L, 10L);

        assertNotNull(response);
        assertEquals("Medicamento salvo com sucesso", response.getMessage());
        assertTrue(animal.getMedicine().contains(medicine));
        verify(animalRepository, times(1)).save(animal);
    }

    @Test
    void createNewAnimal_ShouldReturnSavedAnimal() {
        when(animalRepository.save(any(AnimalEntity.class))).thenReturn(animal);

        AnimalEntity result = animalService.createNewAnimal(animal);

        assertNotNull(result);
        assertEquals("Rex", result.getName());
        verify(animalRepository, times(1)).save(animal);
    }

    @Test
    void updateAnimal_ShouldReturnUpdatedAnimal() {
        AnimalEntity updatedInfo = new AnimalEntity();
        updatedInfo.setName("Rex Updated");
        updatedInfo.setWeight(32.0);
        updatedInfo.setAge(6);
        updatedInfo.setRace("Labrador");

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(animalRepository.save(any(AnimalEntity.class))).thenReturn(animal);

        AnimalEntity result = animalService.updateAnimal(1L, updatedInfo);

        assertNotNull(result);
        assertEquals(32.0, result.getWeight());
        assertEquals(6, result.getAge());
        assertEquals("Labrador", result.getRace());
        verify(animalRepository, times(1)).save(animal);
    }

    @Test
    void updateAnimal_ShouldThrowExceptionWhenNotFound() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        AnimalEntity updatedInfo = new AnimalEntity();
        assertThrows(
                ResourceNotFoundException.class,
                () -> animalService.updateAnimal(1L, updatedInfo)
        );
    }

    @Test
    void deleteById_ShouldDeleteSuccessfullyWhenExists() {
        when(animalRepository.existsById(1L)).thenReturn(true);
        doNothing().when(animalRepository).deleteById(1L);

        assertDoesNotThrow(() -> animalService.deleteById(1L));
        verify(animalRepository, times(1)).existsById(1L);
        verify(animalRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ShouldThrowExceptionWhenNotFound() {
        when(animalRepository.existsById(1L)).thenReturn(false);

        assertThrows(
                ResourceNotFoundException.class,
                () -> animalService.deleteById(1L)
        );
        verify(animalRepository, never()).deleteById(anyLong());
    }
}
