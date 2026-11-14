package com.vetcare.petmeds.modules.images.repository;

import com.vetcare.petmeds.modules.images.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
