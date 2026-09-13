package com.vetcare.petmeds.modules.initialCheckUp.repository;

import com.vetcare.petmeds.modules.initialCheckUp.entity.InitialCheckUpEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface InitialCheckUpRepository extends JpaRepository<InitialCheckUpEntity, Long> {
    Page<InitialCheckUpEntity> findByAnimalId(Long animalId, Pageable pageable);
    Page<InitialCheckUpEntity> findByVeterinarianId(Long veterinarianId, Pageable pageable);
    Page<InitialCheckUpEntity> findByExaminationDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
