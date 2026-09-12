package com.vetcare.petmeds.modules.initialCheckUp.repository;

import com.vetcare.petmeds.modules.initialCheckUp.entity.InitialCheckUpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface InitialCheckUpRepository extends JpaRepository<InitialCheckUpEntity, Long> {
    List<InitialCheckUpEntity> findByAnimalId(Long animalId);
    List<InitialCheckUpEntity> findByVeterinarianId(Long veterinarianId);
    List<InitialCheckUpEntity> findByExaminationDateBetween(LocalDateTime start, LocalDateTime end);
}
