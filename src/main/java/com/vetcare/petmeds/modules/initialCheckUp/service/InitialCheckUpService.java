package com.vetcare.petmeds.modules.initialCheckUp.service;

import com.vetcare.petmeds.exception.ResourceNotFoundException;
import com.vetcare.petmeds.modules.animal.dto.AnimalResponseDTO;
import com.vetcare.petmeds.modules.animal.repository.AnimalRepository;
import com.vetcare.petmeds.modules.initialCheckUp.dto.InitialCheckUpDTO;
import com.vetcare.petmeds.modules.initialCheckUp.dto.InitialCheckUpResponseDTO;
import com.vetcare.petmeds.modules.initialCheckUp.entity.InitialCheckUpEntity;
import com.vetcare.petmeds.modules.initialCheckUp.repository.InitialCheckUpRepository;
import com.vetcare.petmeds.modules.user.dto.UserResponseDTO;
import com.vetcare.petmeds.modules.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class InitialCheckUpService {
    private static final Logger logger = LoggerFactory.getLogger(InitialCheckUpService.class);
    private InitialCheckUpRepository repository;
    private AnimalRepository animalRepository;
    private UserRepository userRepository;

    public InitialCheckUpResponseDTO create(InitialCheckUpDTO dto) {
        InitialCheckUpEntity entity = new InitialCheckUpEntity(dto);

        entity.setAnimal(animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado")));
        entity.setVeterinarian(userRepository.findById(dto.getVeterinarianId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado")));

        InitialCheckUpResponseDTO response = new InitialCheckUpResponseDTO(repository.save(entity));

        logger.info("Check-up inicial criado com sucesso para animal ID: {}", dto.getAnimalId());
        return response;
    }

    public Page<InitialCheckUpResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(InitialCheckUpResponseDTO::new);
    }

    public InitialCheckUpResponseDTO findById(Long id) {
        return new InitialCheckUpResponseDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checkup não encontrado")));
    }

    public InitialCheckUpEntity findByIdEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checkup não encontrado"));
    }

    public InitialCheckUpResponseDTO update(Long id, InitialCheckUpDTO dto) {
        InitialCheckUpEntity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checkup não encontrado"));

        existing.setAnimal(animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado")));

        existing.setVeterinarian(userRepository.findById(dto.getVeterinarianId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado")));

        logger.info("Check-up inicial atualizado com sucesso ID: {}", id);
        return new InitialCheckUpResponseDTO(repository.save(existing));
    }

    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Checkup não encontrado"));
        repository.deleteById(id);
        logger.info("Check-up inicial deletado com sucesso ID: {}", id);
    }

    public Page<InitialCheckUpResponseDTO> findByAnimalId(Long animalId, Pageable pageable) {
        logger.info("Busca de check-ups por animal ID obtida com sucesso");
        return repository.findByAnimalId(animalId, pageable).map(InitialCheckUpResponseDTO::new);
    }

    public Page<InitialCheckUpResponseDTO> findByVeterinarianId(Long veterinarianId, Pageable pageable) {
        return repository.findByVeterinarianId(veterinarianId, pageable).map(InitialCheckUpResponseDTO::new);
    }

    public Page<InitialCheckUpResponseDTO> findByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return repository.findByExaminationDateBetween(start, end, pageable).map(InitialCheckUpResponseDTO::new);
    }
}
