package com.vetcare.petmeds.applicationPrimary.vaccinationRecord.controller;

import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.dto.RequestEditVaccinationRecord;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.dto.VaccinationRecordCreateDTO;
import com.vetcare.petmeds.applicationPrimary.vaccinationRecord.dto.VaccinationRecordResponseDTO;
import com.vetcare.petmeds.shared.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

public interface VaccinationRecordControllerInterface {
    @PostMapping
    @Operation(
            summary = "Cadastrar registro de vacina",
            description = "Cadastra um novo registro de aplicação de vacina no sistema"
    )
    ResponseEntity<ResponseDTO> create(@RequestBody VaccinationRecordCreateDTO newVaccinationRecord);

    @GetMapping("/id/{id}")
    @Operation(
            summary = "Buscar registro de vacina por ID",
            description = "Busca as informações detalhadas de um registro de vacina através do seu ID"
    )
    ResponseEntity<VaccinationRecordResponseDTO> findById(@PathVariable Long id);

    @GetMapping("/all")
    @Operation(
            summary = "Listar todos os registros de vacinas",
            description = "Retorna uma lista paginada de todos os registros de vacina cadastrados"
    )
    ResponseEntity<Page<VaccinationRecordResponseDTO>> getAllVaccinationRecord(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @GetMapping("/animal/{idAnimal}")
    @Operation(
            summary = "Buscar registros de vacina por ID do animal",
            description = "Retorna uma lista paginada de registros de vacina associados a um animal específico"
    )
    ResponseEntity<Page<VaccinationRecordResponseDTO>> getVaccinationRecordByIdAnimal(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @PathVariable Long idAnimal
    );

    @GetMapping("/medic/{medic}")
    @Operation(
            summary = "Buscar registros de vacina por ID do médico",
            description = "Retorna uma lista paginada de registros de vacina aplicados por um médico específico"
    )
    ResponseEntity<Page<VaccinationRecordResponseDTO>> getVaccinationRecordByIdMedic(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @PathVariable Long medic
    );

    @GetMapping("/vaccine/{vaccine}")
    @Operation(
            summary = "Buscar registros de vacina por ID da vacina",
            description = "Retorna uma lista paginada de registros de vacina associados a uma vacina específica"
    )
    ResponseEntity<Page<VaccinationRecordResponseDTO>> getVaccinationRecordByIdVaccine(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @PathVariable Long vaccine
    );

    @GetMapping("/date")
    @Operation(
            summary = "Buscar registros de vacina por data",
            description = "Retorna uma lista paginada de registros de vacina filtrados por uma data específica"
    )
    ResponseEntity<Page<VaccinationRecordResponseDTO>> getVaccinationRecordByDate(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    );

    @GetMapping("/between-date")
    @Operation(
            summary = "Buscar registros de vacina por intervalo de datas",
            description = "Retorna uma lista paginada de registros de vacina filtrados entre uma data inicial e final"
    )
    ResponseEntity<Page<VaccinationRecordResponseDTO>> getVaccinationRecordByBetweenDate(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );

    @GetMapping("/status")
    @Operation(
            summary = "Buscar registros de vacina por status",
            description = "Retorna uma lista paginada de registros de vacina filtrados pelo status de aplicação"
    )
    ResponseEntity<Page<VaccinationRecordResponseDTO>> getVaccinationRecordByStatus(
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            @RequestParam String status
    );

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar registro de vacina",
            description = "Atualiza os dados de um registro de vacina existente através do seu ID"
    )
    ResponseEntity<VaccinationRecordResponseDTO> edit(@Valid @RequestBody RequestEditVaccinationRecord request, @PathVariable Long id);
}
