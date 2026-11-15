package com.vetcare.petmeds.applicationPrimary.medicine.repository;

import com.vetcare.petmeds.applicationPrimary.medicine.entity.MedicineEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<MedicineEntity, Long> {
    Page<MedicineEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
