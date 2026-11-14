package com.vetcare.petmeds.model.history;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.animal.entity.Sex;
import com.vetcare.petmeds.modules.animal.service.AnimalService;
import com.vetcare.petmeds.modules.history.dto.HistoryRequestDTO;
import com.vetcare.petmeds.modules.history.dto.HistoryResponseDTO;
import com.vetcare.petmeds.modules.history.entity.HistoryEntity;
import com.vetcare.petmeds.modules.history.repository.HistoryRepository;
import com.vetcare.petmeds.modules.history.service.HistoryService;
import com.vetcare.petmeds.modules.initialCheckUp.entity.InitialCheckUpEntity;
import com.vetcare.petmeds.modules.initialCheckUp.service.InitialCheckUpService;
import com.vetcare.petmeds.modules.user.entity.UserEntity;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HistoryServiceTest {

    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private AnimalService animalService;

    @Mock
    private InitialCheckUpService initialCheckUpService;

    @InjectMocks
    private HistoryService historyService;

    private HistoryEntity historyEntity;
    private AnimalEntity animalEntity;
    private InitialCheckUpEntity checkUpEntity;
    private HistoryRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        UserEntity owner = new UserEntity();
        owner.setName("John Doe");

        animalEntity = new AnimalEntity();
        animalEntity.setId(1L);
        animalEntity.setName("Rex");
        animalEntity.setGender(Sex.MAS);
        animalEntity.setOwner(owner);

        UserEntity vet = new UserEntity();
        vet.setId(2L);

        checkUpEntity = new InitialCheckUpEntity();
        checkUpEntity.setId(1L);
        checkUpEntity.setVeterinarian(vet);

        historyEntity = new HistoryEntity();
        historyEntity.setId(1L);
        historyEntity.setAnimal(animalEntity);
        historyEntity.setCheckUp(checkUpEntity);

        requestDTO = new HistoryRequestDTO(1L, 1L);
    }

    @Test
    void create_ShouldReturnHistoryResponse() {
        when(animalService.getById(1L)).thenReturn(animalEntity);
        when(initialCheckUpService.findByIdEntity(1L)).thenReturn(checkUpEntity);
        when(historyRepository.save(any(HistoryEntity.class))).thenReturn(historyEntity);

        HistoryResponseDTO result = historyService.create(requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Rex", result.getNameAnimal());
        verify(historyRepository, times(1)).save(any(HistoryEntity.class));
    }

    @Test
    void update_ShouldReturnUpdatedHistoryResponse() {
        when(historyRepository.findById(1L)).thenReturn(Optional.of(historyEntity));
        when(animalService.getById(1L)).thenReturn(animalEntity);
        when(initialCheckUpService.findByIdEntity(1L)).thenReturn(checkUpEntity);
        when(historyRepository.save(any(HistoryEntity.class))).thenReturn(historyEntity);

        HistoryResponseDTO result = historyService.update(1L, requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(historyRepository, times(1)).save(any(HistoryEntity.class));
    }

    @Test
    void update_ShouldThrowExceptionWhenNotFound() {
        when(historyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> historyService.update(1L, requestDTO));
        verify(historyRepository, never()).save(any(HistoryEntity.class));
    }

    @Test
    void findById_ShouldReturnHistoryResponseWhenExists() {
        when(historyRepository.findById(1L)).thenReturn(Optional.of(historyEntity));

        HistoryResponseDTO result = historyService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(historyRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldThrowExceptionWhenNotFound() {
        when(historyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> historyService.findById(1L));
        verify(historyRepository, times(1)).findById(1L);
    }

    @Test
    void findAll_ShouldReturnPageOfHistories() {
        Page<HistoryEntity> page = new PageImpl<>(Arrays.asList(historyEntity));
        when(historyRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<HistoryResponseDTO> result = historyService.findAll(Pageable.unpaged());

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(historyRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void delete_ShouldDeleteWhenExists() {
        when(historyRepository.existsById(1L)).thenReturn(true);
        doNothing().when(historyRepository).deleteById(1L);

        assertDoesNotThrow(() -> historyService.delete(1L));
        verify(historyRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_ShouldThrowExceptionWhenNotFound() {
        when(historyRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> historyService.delete(1L));
        verify(historyRepository, never()).deleteById(anyLong());
    }
}
