package com.vetcare.petmeds.modules.medicine.repository;

import com.vetcare.petmeds.modules.medicine.entity.MedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<MedicineEntity, Long> {
    List<MedicineEntity> findByNameContainingIgnoreCase(String name);
}
