package com.vetcare.petmeds.applicationPrimary.history.service;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.applicationPrimary.animal.entity.AnimalEntity;
import com.vetcare.petmeds.applicationPrimary.animal.service.AnimalService;
import com.vetcare.petmeds.applicationPrimary.history.dto.HistoryRequestDTO;
import com.vetcare.petmeds.applicationPrimary.history.dto.HistoryResponseDTO;
import com.vetcare.petmeds.applicationPrimary.history.entity.HistoryEntity;
import com.vetcare.petmeds.applicationPrimary.history.repository.HistoryRepository;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.entity.InitialCheckUpEntity;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.service.InitialCheckUpService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class HistoryService {
    private static final Logger logger = LoggerFactory.getLogger(HistoryService.class);
    HistoryRepository repository;
    AnimalService animalService;
    InitialCheckUpService initialCheckUpService;

    public HistoryResponseDTO create(HistoryRequestDTO requestDTO) {
        AnimalEntity animal = animalService.getById(requestDTO.getIdAnimal());
        InitialCheckUpEntity checkUp = initialCheckUpService.findByIdEntity(requestDTO.getIdLastCheckup());

        HistoryEntity history = new HistoryEntity();
        history.setAnimal(animal);
        history.setCheckUp(checkUp);

        return new HistoryResponseDTO(repository.save(history));
    }

    public HistoryResponseDTO update(Long id, HistoryRequestDTO requestDTO) {
        HistoryEntity history = findEntityById(id);
        AnimalEntity animal = animalService.getById(requestDTO.getIdAnimal());
        InitialCheckUpEntity checkUp = initialCheckUpService.findByIdEntity(requestDTO.getIdLastCheckup());

        history.setAnimal(animal);
        history.setCheckUp(checkUp);

        return new HistoryResponseDTO(repository.save(history));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Histórico não encontrado.");
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public HistoryResponseDTO findById(Long id) {
        return new HistoryResponseDTO(repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Histórico não encontrado.")));
    }

    public HistoryEntity findEntityById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Não foi encontrado o historico com o ID informado.")
        );
    }

    @Transactional(readOnly = true)
    public Page<HistoryResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(HistoryResponseDTO::new);
    }
}
