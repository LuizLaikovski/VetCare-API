package com.vetcare.petmeds.applicationPrimary.animal.repository;

import com.vetcare.petmeds.applicationPrimary.animal.entity.AnimalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Long> {

    Page<AnimalEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
