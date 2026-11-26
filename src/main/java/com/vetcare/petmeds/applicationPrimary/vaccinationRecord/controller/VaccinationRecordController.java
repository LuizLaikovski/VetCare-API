package com.vetcare.petmeds.applicationPrimary.vaccinationRecord.controller;

import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.dto.RequestEditVaccinationRecord;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.dto.VaccinationRecordCreateDTO;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.dto.VaccinationRecordResponseDTO;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.service.VaccinationRecordService;
import com.vetcare.petmeds.shared.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor
@RestController
@RequestMapping("/vaccination-records")
@Tag(name = "Aplicação de Vacina", description = "Nessa rota gerenciamos as aplicações de vacinas")
public class VaccinationRecordController implements VaccinationRecordControllerInterface {
    private VaccinationRecordService vaccinationRecordService;

    @Override
    public ResponseEntity<ResponseDTO> create(@RequestBody VaccinationRecordCreateDTO newVaccinationRecord) {
        vaccinationRecordService.createVaccinationRecord(newVaccinationRecord);
        ResponseDTO response = new ResponseDTO(
                new Date(),
                "Registro de Vacina cadastrado com sucesso",
                "Registro de Vacina cadastrado com sucesso"
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<VaccinationRecordResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vaccinationRecordService.findById(id));
    }

    @Override
    public ResponseEntity<Page<VaccinationRecordResponseDTO>> getAllVaccinationRecord(
            Long animalId, Long medicId, Long vaccineId, String status,
            LocalDate date, LocalDate startDate, LocalDate endDate, Pageable pageable
    ) {
        if (animalId != null) {
            return ResponseEntity.ok(vaccinationRecordService.findByAnimalId(animalId, pageable));
        }
        if (medicId != null) {
            return ResponseEntity.ok(vaccinationRecordService.findByMedicId(medicId, pageable));
        }
        if (vaccineId != null) {
            return ResponseEntity.ok(vaccinationRecordService.findByVaccineId(vaccineId, pageable));
        }
        if (status != null && !status.isBlank()) {
            return ResponseEntity.ok(vaccinationRecordService.findByApplicationStatus(status, pageable));
        }
        if (date != null) {
            Date dateFind = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            return ResponseEntity.ok(vaccinationRecordService.findByApplicationDate(dateFind, pageable));
        }
        if (startDate != null || endDate != null) {
            if (startDate == null || endDate == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe startDate e endDate juntos");
            }
            Date startDateFind = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDateFind = Date.from(endDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
            return ResponseEntity.ok(vaccinationRecordService.findByDateRange(startDateFind, endDateFind, pageable));
        }
        return ResponseEntity.ok(vaccinationRecordService.findAll(pageable));
    }

    @Override
    public ResponseEntity<VaccinationRecordResponseDTO> edit(@Valid @RequestBody RequestEditVaccinationRecord request, @PathVariable Long id) {
        return new ResponseEntity<>(vaccinationRecordService.editVaccinationRecord(request, id), HttpStatus.OK);
    }
}
