package com.vetcare.petmeds.applicationPrimary.history.repository;

import com.vetcare.petmeds.applicationPrimary.history.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
}
