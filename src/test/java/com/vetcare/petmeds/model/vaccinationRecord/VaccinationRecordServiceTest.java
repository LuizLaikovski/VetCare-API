package com.vetcare.petmeds.model.vaccinationRecord;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import com.vetcare.petmeds.modules.animal.repository.AnimalRepository;
import com.vetcare.petmeds.modules.user.entity.UserEntity;
import com.vetcare.petmeds.modules.user.repository.UserRepository;
import com.vetcare.petmeds.modules.vaccinationRecord.dto.RequestEditVaccinationRecord;
import com.vetcare.petmeds.modules.vaccinationRecord.dto.VaccinationRecordCreateDTO;
import com.vetcare.petmeds.modules.vaccinationRecord.dto.VaccinationRecordResponseDTO;
import com.vetcare.petmeds.modules.vaccinationRecord.entity.ApplicationStatus;
import com.vetcare.petmeds.modules.vaccinationRecord.entity.VaccinationRecordEntity;
import com.vetcare.petmeds.modules.vaccinationRecord.repository.VaccinationRecordRepository;
import com.vetcare.petmeds.modules.vaccinationRecord.service.VaccinationRecordService;
import com.vetcare.petmeds.modules.vaccine.entity.VaccineEntity;
import com.vetcare.petmeds.modules.vaccine.repository.VaccineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VaccinationRecordServiceTest {

    @Mock
    private VaccinationRecordRepository vaccinationRecordRepository;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VaccineRepository vaccineRepository;

    @InjectMocks
    private VaccinationRecordService service;

    private VaccinationRecordEntity recordEntity;
    private AnimalEntity animalEntity;
    private UserEntity userEntity;
    private VaccineEntity vaccineEntity;
    private VaccinationRecordCreateDTO createDTO;

    @BeforeEach
    void setUp() {
        animalEntity = new AnimalEntity();
        animalEntity.setId(1L);
        animalEntity.setName("Rex");

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Dr. Vet");

        vaccineEntity = new VaccineEntity();
        vaccineEntity.setId(1L);
        vaccineEntity.setVaccineName("Raiva");
        vaccineEntity.setNumOfDoses(1);

        recordEntity = new VaccinationRecordEntity();
        recordEntity.setId(1L);
        recordEntity.setAnimal(animalEntity);
        recordEntity.setMedic(userEntity);
        recordEntity.setVaccine(vaccineEntity);
        recordEntity.setApplicationDate(new Date());
        recordEntity.setNumOfDoses(1);
        recordEntity.setApplicationStatus(ApplicationStatus.APPLIED);

        createDTO = new VaccinationRecordCreateDTO();
        createDTO.setAnimalId(1L);
        createDTO.setVaccineId(1L);
        createDTO.setMedicId(1L);
        createDTO.setNumOfDoses(1);
        createDTO.setApplicationStatus("APPLIED");
    }

    @Test
    void createVaccinationRecord_ShouldReturnResponse() {
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animalEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(vaccineRepository.findById(1L)).thenReturn(Optional.of(vaccineEntity));
        when(vaccinationRecordRepository.save(any(VaccinationRecordEntity.class))).thenReturn(recordEntity);

        VaccinationRecordResponseDTO result = service.createVaccinationRecord(createDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(vaccinationRecordRepository, times(1)).save(any(VaccinationRecordEntity.class));
    }

    @Test
    void createVaccinationRecord_ShouldThrowExceptionWhenAnimalNotFound() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.createVaccinationRecord(createDTO));
        verify(vaccinationRecordRepository, never()).save(any());
    }

    @Test
    void findById_ShouldReturnResponseWhenExists() {
        when(vaccinationRecordRepository.findById(1L)).thenReturn(Optional.of(recordEntity));

        VaccinationRecordResponseDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(vaccinationRecordRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldThrowExceptionWhenNotFound() {
        when(vaccinationRecordRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
        verify(vaccinationRecordRepository, times(1)).findById(1L);
    }

    @Test
    void findAll_ShouldReturnPage() {
        Page<VaccinationRecordEntity> page = new PageImpl<>(Arrays.asList(recordEntity));
        when(vaccinationRecordRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<VaccinationRecordResponseDTO> result = service.findAll(Pageable.unpaged());

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(vaccinationRecordRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void findByAnimalId_ShouldReturnPage() {
        Page<VaccinationRecordEntity> page = new PageImpl<>(Arrays.asList(recordEntity));
        when(vaccinationRecordRepository.findByAnimalId(eq(1L), any(Pageable.class))).thenReturn(page);

        Page<VaccinationRecordResponseDTO> result = service.findByAnimalId(1L, Pageable.unpaged());

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(vaccinationRecordRepository, times(1)).findByAnimalId(eq(1L), any(Pageable.class));
    }

    @Test
    void findByMedicId_ShouldReturnPage() {
        Page<VaccinationRecordEntity> page = new PageImpl<>(Arrays.asList(recordEntity));
        when(vaccinationRecordRepository.findByMedicId(eq(1L), any(Pageable.class))).thenReturn(page);

        Page<VaccinationRecordResponseDTO> result = service.findByMedicId(1L, Pageable.unpaged());

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(vaccinationRecordRepository, times(1)).findByMedicId(eq(1L), any(Pageable.class));
    }

    @Test
    void findByVaccineId_ShouldReturnPage() {
        Page<VaccinationRecordEntity> page = new PageImpl<>(Arrays.asList(recordEntity));
        when(vaccinationRecordRepository.findByVaccineId(eq(1L), any(Pageable.class))).thenReturn(page);

        Page<VaccinationRecordResponseDTO> result = service.findByVaccineId(1L, Pageable.unpaged());

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(vaccinationRecordRepository, times(1)).findByVaccineId(eq(1L), any(Pageable.class));
    }

    @Test
    void findByApplicationStatus_ShouldReturnPage() {
        Page<VaccinationRecordEntity> page = new PageImpl<>(Arrays.asList(recordEntity));
        when(vaccinationRecordRepository.findByApplicationStatus(eq(ApplicationStatus.APPLIED), any(Pageable.class))).thenReturn(page);

        Page<VaccinationRecordResponseDTO> result = service.findByApplicationStatus("APPLIED", Pageable.unpaged());

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        verify(vaccinationRecordRepository, times(1)).findByApplicationStatus(eq(ApplicationStatus.APPLIED), any(Pageable.class));
    }

    @Test
    void editVaccinationRecord_ShouldReturnUpdatedResponse() {
        when(vaccinationRecordRepository.findById(1L)).thenReturn(Optional.of(recordEntity));
        when(vaccinationRecordRepository.save(any(VaccinationRecordEntity.class))).thenReturn(recordEntity);

        RequestEditVaccinationRecord editRequest = new RequestEditVaccinationRecord();
        editRequest.setApplicationDate(new Date());
        editRequest.setNumOfDoses(2);
        editRequest.setObservation("Updated observation");

        VaccinationRecordResponseDTO result = service.editVaccinationRecord(editRequest, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(vaccinationRecordRepository, times(1)).save(any(VaccinationRecordEntity.class));
    }
}
