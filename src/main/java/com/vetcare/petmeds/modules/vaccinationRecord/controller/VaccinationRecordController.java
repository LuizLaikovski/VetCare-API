package com.vetcare.petmeds.modules.vaccinationRecord.controller;

import com.vetcare.petmeds.modules.vaccinationRecord.dto.VaccinationRecordCreateDTO;
import com.vetcare.petmeds.modules.vaccinationRecord.dto.VaccinationRecordResponseDTO;
import com.vetcare.petmeds.modules.vaccinationRecord.service.VaccinationRecordService;
import com.vetcare.petmeds.shared.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@AllArgsConstructor
@RestController
@RequestMapping("/vaccination-record")
@Tag(name = "Aplicação de Vacina", description = "Nessa rota gerenciamos as aplicações de vacinas")
public class VaccinationRecordController {
    private VaccinationRecordService vaccinationRecordService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> create(@RequestBody VaccinationRecordCreateDTO newVaccinationRecord) {
        vaccinationRecordService.createVaccinationRecord(newVaccinationRecord);
        ResponseDTO response = new ResponseDTO(
                new Date(),
                "Registro de Vacina cadastrado com sucesso",
                "Registro de Vacina cadastrado com sucesso"
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<VaccinationRecordResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vaccinationRecordService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<VaccinationRecordResponseDTO>> getAllVaccinationRecord(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(vaccinationRecordService.findAll(pageable));
    }

    @GetMapping("/animal/{idAnimal}")
    public ResponseEntity<Page<VaccinationRecordResponseDTO>> getVaccinationRecordByIdAnimal(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @PathVariable Long idAnimal
    ) {
        return ResponseEntity.ok(vaccinationRecordService.findByAnimalId(idAnimal, pageable));
    }

    @GetMapping("/medic/{medic}")
    public ResponseEntity<Page<VaccinationRecordResponseDTO>> getVaccinationRecordByIdMedic(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @PathVariable Long medic
    ) {
        return ResponseEntity.ok(vaccinationRecordService.findByMedicId(medic, pageable));
    }

    @GetMapping("/vaccine/{vaccine}")
    public ResponseEntity<Page<VaccinationRecordResponseDTO>> getVaccinationRecordByIdVaccine(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @PathVariable Long vaccine
    ) {
        return ResponseEntity.ok(vaccinationRecordService.findByVaccineId(vaccine, pageable));
    }

    @GetMapping("/date")
    public ResponseEntity<Page<VaccinationRecordResponseDTO>> getVaccinationRecordByDate(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        Date startDateFind = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDateFind = Date.from(date.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
        return ResponseEntity.ok(vaccinationRecordService.findByDateRange(startDateFind, endDateFind, pageable));
    }

    @GetMapping("/between-date")
    public ResponseEntity<Page<VaccinationRecordResponseDTO>> getVaccinationRecordByBetweenDate(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        Date startDateFind = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDateFind = Date.from(endDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());
        return ResponseEntity.ok(vaccinationRecordService.findByDateRange(startDateFind, endDateFind, pageable));
    }

    @GetMapping("/status")
    public ResponseEntity<Page<VaccinationRecordResponseDTO>> getVaccinationRecordByStatus(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(vaccinationRecordService.findByApplicationStatus(status, pageable));
    }
}
