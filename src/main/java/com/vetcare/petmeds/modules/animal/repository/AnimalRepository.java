package com.vetcare.petmeds.modules.animal.repository;

import com.vetcare.petmeds.modules.animal.entity.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Long> {

    Optional<AnimalEntity> findByName(String name);
}
