package com.vetcare.petmeds.modules.history.repository;

import com.vetcare.petmeds.modules.history.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
}
