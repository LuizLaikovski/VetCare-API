package com.vetcare.petmeds.modules.VaccinationRecord.repository;

import com.vetcare.petmeds.modules.VaccinationRecord.entity.VaccinationRecordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VaccinationRecordRepository extends JpaRepository<VaccinationRecordEntity, Long> {
    Page<VaccinationRecordEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
