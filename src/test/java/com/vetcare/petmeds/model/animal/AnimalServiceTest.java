package com.vetcare.petmeds.model.animal;

import com.vetcare.petmeds.modules.user.dto.ResponseDTO;
import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.animal.repository.AnimalRepository;
import com.vetcare.petmeds.modules.animal.service.AnimalService;
import com.vetcare.petmeds.modules.animal.entity.Specie;
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
public class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

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
        when(animalRepository.findAll()).thenReturn(animals);

        List<AnimalEntity> result = animalService.getAll();

        assertEquals(1, result.size());
        assertEquals("Rex", result.get(0).getName());
        verify(animalRepository, times(1)).findAll();
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

        Exception exception = assertThrows(RuntimeException.class, () -> animalService.getById(1L));

        assertEquals("Animal não encontrado", exception.getMessage());
    }

    @Test
    void getByName_ShouldReturnAnimalWhenExists() {
        when(animalRepository.findByName("Rex")).thenReturn(Optional.of(animal));

        Optional<AnimalEntity> result = animalService.getByName("Rex");

        assertTrue(result.isPresent());
        assertEquals("Rex", result.get().getName());
        verify(animalRepository, times(1)).findByName("Rex");
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

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        when(animalRepository.save(any(AnimalEntity.class))).thenReturn(animal);

        AnimalEntity result = animalService.updateAnimal(1L, updatedInfo);

        assertNotNull(result);
        assertEquals("Rex Updated", result.getName());
        assertEquals(32.0, result.getWeight());
        verify(animalRepository, times(1)).save(animal);
    }

    @Test
    void deleteById_ShouldReturnSuccessResponse() {
        doNothing().when(animalRepository).deleteById(1L);

        ResponseDTO response = animalService.deleteById(1L);

        assertEquals("Animal do id 1 deletado com sucesso", response.getResponse());
        verify(animalRepository, times(1)).deleteById(1L);
    }

    @Test
    void createAnimals_ShouldReturnSuccessResponse() {
        List<AnimalEntity> animals = Arrays.asList(animal, new AnimalEntity());
        when(animalRepository.save(any(AnimalEntity.class))).thenReturn(animal);

        ResponseDTO response = animalService.createAnimals(animals);

        assertTrue(response.getResponse().contains("cadastrado com sucesso"));
        verify(animalRepository, times(2)).save(any(AnimalEntity.class));
    }
}
