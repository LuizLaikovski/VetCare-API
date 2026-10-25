package com.vetcare.petmeds.modules.vaccinationRecord.repository;

import com.vetcare.petmeds.modules.vaccinationRecord.entity.ApplicationStatus;
import com.vetcare.petmeds.modules.vaccinationRecord.entity.VaccinationRecordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface VaccinationRecordRepository extends JpaRepository<VaccinationRecordEntity, Long> {

    Page<VaccinationRecordEntity> findByAnimalId(Long animalId, Pageable pageable);

    Page<VaccinationRecordEntity> findByMedicId(Long medicId, Pageable pageable);

    Page<VaccinationRecordEntity> findByVaccineId(Long vaccineId, Pageable pageable);

    Page<VaccinationRecordEntity> findByApplicationDate(Date applicationDate, Pageable pageable);

    Page<VaccinationRecordEntity> findByApplicationDateBetween(Date startDate, Date endDate, Pageable pageable);

    Page<VaccinationRecordEntity> findByApplicationStatus(ApplicationStatus applicationStatus, Pageable pageable);

}
