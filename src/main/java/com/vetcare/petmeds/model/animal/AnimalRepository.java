package com.vetcare.petmeds.model.animal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Long> {

    Optional<AnimalEntity> findByName(String name);
}
