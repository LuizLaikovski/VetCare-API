package com.vetcare.petmeds.archives.images.repository;

import com.vetcare.petmeds.archives.images.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
