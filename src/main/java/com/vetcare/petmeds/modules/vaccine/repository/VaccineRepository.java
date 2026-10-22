package com.vetcare.petmeds.modules.vaccine.repository;

import com.vetcare.petmeds.modules.vaccine.entity.VaccineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineRepository extends JpaRepository<VaccineEntity, Long> {

    Page<VaccineEntity> findByVaccineNameContainingIgnoreCase(String vaccineName, Pageable pageable);

}
