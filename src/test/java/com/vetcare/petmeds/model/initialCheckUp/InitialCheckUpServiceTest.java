package com.vetcare.petmeds.model.initialCheckUp;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.applicationPrimary.animal.entity.AnimalEntity;
import com.vetcare.petmeds.applicationPrimary.animal.repository.AnimalRepository;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.dto.InitialCheckUpDTO;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.dto.InitialCheckUpResponseDTO;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.entity.InitialCheckUpEntity;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.repository.InitialCheckUpRepository;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.service.InitialCheckUpService;
import com.vetcare.petmeds.applicationPrimary.user.entity.UserEntity;
import com.vetcare.petmeds.applicationPrimary.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InitialCheckUpServiceTest {

    @Mock
    private InitialCheckUpRepository repository;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private InitialCheckUpService service;

    private InitialCheckUpEntity checkUpEntity;
    private AnimalEntity animalEntity;
    private UserEntity userEntity;
    private InitialCheckUpDTO dto;

    @BeforeEach
    void setUp() {
        animalEntity = new AnimalEntity();
        animalEntity.setId(1L);
        animalEntity.setName("Rex");

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Dr. Vet");

        checkUpEntity = new InitialCheckUpEntity();
        checkUpEntity.setId(1L);
        checkUpEntity.setExaminationDate(LocalDateTime.now());
        checkUpEntity.setAnimal(animalEntity);
        checkUpEntity.setVeterinarian(userEntity);

        dto = new InitialCheckUpDTO();
        dto.setAnimalId(1L);
        dto.setVeterinarianId(1L);
    }

    @Test
    void create_ShouldReturnResponse() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animalEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(repository.save(any(InitialCheckUpEntity.class))).thenReturn(checkUpEntity);

        InitialCheckUpResponseDTO result = service.create(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(InitialCheckUpEntity.class));
    }

    @Test
    void create_ShouldThrowExceptionWhenAnimalNotFound() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.create(dto));
        verify(repository, never()).save(any());
    }

    @Test
    void findAll_ShouldReturnPage() {
        Page<InitialCheckUpEntity> page = new PageImpl<>(Arrays.asList(checkUpEntity));
        when(repository.findAll(any(Pageable.class))).thenReturn(page);

        Page<InitialCheckUpResponseDTO> result = service.findAll(Pageable.unpaged());

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(repository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void findById_ShouldReturnResponseWhenExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(checkUpEntity));

        InitialCheckUpResponseDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldThrowExceptionWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void update_ShouldReturnUpdatedResponse() {
        when(repository.findById(1L)).thenReturn(Optional.of(checkUpEntity));
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animalEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(repository.save(any(InitialCheckUpEntity.class))).thenReturn(checkUpEntity);

        InitialCheckUpResponseDTO result = service.update(1L, dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(InitialCheckUpEntity.class));
    }

    @Test
    void update_ShouldThrowExceptionWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(1L, dto));
        verify(repository, never()).save(any());
    }

    @Test
    void delete_ShouldDeleteWhenExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(checkUpEntity));
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void delete_ShouldThrowExceptionWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    void findByAnimalId_ShouldReturnPage() {
        Page<InitialCheckUpEntity> page = new PageImpl<>(Arrays.asList(checkUpEntity));
        when(repository.findByAnimalId(eq(1L), any(Pageable.class))).thenReturn(page);

        Page<InitialCheckUpResponseDTO> result = service.findByAnimalId(1L, Pageable.unpaged());

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(repository, times(1)).findByAnimalId(eq(1L), any(Pageable.class));
    }

    @Test
    void findByVeterinarianId_ShouldReturnPage() {
        Page<InitialCheckUpEntity> page = new PageImpl<>(Arrays.asList(checkUpEntity));
        when(repository.findByVeterinarianId(eq(1L), any(Pageable.class))).thenReturn(page);

        Page<InitialCheckUpResponseDTO> result = service.findByVeterinarianId(1L, Pageable.unpaged());

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(repository, times(1)).findByVeterinarianId(eq(1L), any(Pageable.class));
    }

    @Test
    void findByDateRange_ShouldReturnPage() {
        Page<InitialCheckUpEntity> page = new PageImpl<>(Arrays.asList(checkUpEntity));
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        when(repository.findByExaminationDateBetween(any(), any(), any(Pageable.class))).thenReturn(page);

        Page<InitialCheckUpResponseDTO> result = service.findByDateRange(start, end, Pageable.unpaged());

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(repository, times(1)).findByExaminationDateBetween(any(), any(), any(Pageable.class));
    }
}
