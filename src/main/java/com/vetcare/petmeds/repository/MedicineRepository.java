package com.vetcare.petmeds.repository;

import com.vetcare.petmeds.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
}
