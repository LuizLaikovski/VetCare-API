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
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class InitialCheckUpService {
    private InitialCheckUpRepository repository;
    private AnimalRepository animalRepository;
    private UserRepository userRepository;

    public InitialCheckUpResponseDTO mapToDTO(InitialCheckUpEntity entity) {
        AnimalResponseDTO animalDTO = AnimalResponseDTO.builder()
                .id(entity.getAnimal().getId())
                .name(entity.getAnimal().getName())
                .specie(entity.getAnimal().getSpecie())
                .race(entity.getAnimal().getRace())
                .gender(entity.getAnimal().getGender())
                .build();

        UserResponseDTO vetDTO = new UserResponseDTO(
                entity.getVeterinarian().getId(),
                entity.getVeterinarian().getName(),
                entity.getVeterinarian().getEmail(),
                entity.getVeterinarian().getTypeUser()
        );

        return InitialCheckUpResponseDTO.builder()
                .id(entity.getId())
                .examinationDate(entity.getExaminationDate())
                .species(entity.getSpecies())
                .sex(entity.getSex())
                .animal(animalDTO)
                .veterinarian(vetDTO)
                .crt(entity.getCrt())
                .rr(entity.getRr())
                .hr(entity.getHr())
                .tpc(entity.getTpc())
                .hydration(entity.getHydration())
                .arterialPulse(entity.getArterialPulse())
                .mucousMembranes(entity.getMucousMembranes())
                .consciousnessLevel(entity.getConsciousnessLevel())
                .nutritionalStatus(entity.getNutritionalStatus())
                .behavior(entity.getBehavior())
                .hasEctoparasites(entity.getHasEctoparasites())
                .ectoparasitesDetails(entity.getEctoparasitesDetails())
                .postureMovement(entity.getPostureMovement())
                .eyes(entity.getEyes())
                .ears(entity.getEars())
                .oralCavity(entity.getOralCavity())
                .respiratory(entity.getRespiratory())
                .circulatory(entity.getCirculatory())
                .hemolymphatic(entity.getHemolymphatic())
                .digestive(entity.getDigestive())
                .genital(entity.getGenital())
                .urinary(entity.getUrinary())
                .nervous(entity.getNervous())
                .skinAndAppendages(entity.getSkinAndAppendages())
                .otherStatus(entity.getOtherStatus())
                .otherObservations(entity.getOtherObservations())
                .build();
    }

    public InitialCheckUpResponseDTO create(InitialCheckUpDTO dto) {
        InitialCheckUpEntity entity = new InitialCheckUpEntity();
        BeanUtils.copyProperties(dto, entity);
        
        entity.setAnimal(animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado")));
        entity.setVeterinarian(userRepository.findById(dto.getVeterinarianId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado")));
        
        return mapToDTO(repository.save(entity));
    }

    public Page<InitialCheckUpResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToDTO);
    }

    public InitialCheckUpResponseDTO findById(Long id) {
        return mapToDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checkup não encontrado")));
    }

    public InitialCheckUpResponseDTO update(Long id, InitialCheckUpDTO dto) {
        InitialCheckUpEntity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checkup não encontrado"));
        BeanUtils.copyProperties(dto, existing, "id");
        
        existing.setAnimal(animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal não encontrado")));
        existing.setVeterinarian(userRepository.findById(dto.getVeterinarianId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado")));
        
        return mapToDTO(repository.save(existing));
    }

    public void delete(Long id) {
        InitialCheckUpEntity initialCheckUpEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Checkup não encontrado"));

        repository.deleteById(id);
    }

    public Page<InitialCheckUpResponseDTO> findByAnimalId(Long animalId, Pageable pageable) {
        return repository.findByAnimalId(animalId, pageable).map(this::mapToDTO);
    }

    public Page<InitialCheckUpResponseDTO> findByVeterinarianId(Long veterinarianId, Pageable pageable) {
        return repository.findByVeterinarianId(veterinarianId, pageable).map(this::mapToDTO);
    }

    public Page<InitialCheckUpResponseDTO> findByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return repository.findByExaminationDateBetween(start, end, pageable).map(this::mapToDTO);
    }
}
