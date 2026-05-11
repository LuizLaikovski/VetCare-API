package com.vetcare.petmeds.model.medicine;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<MedicineEntity, Long> {
    List<MedicineEntity> findByNameContainingIgnoreCase(String name);
}
