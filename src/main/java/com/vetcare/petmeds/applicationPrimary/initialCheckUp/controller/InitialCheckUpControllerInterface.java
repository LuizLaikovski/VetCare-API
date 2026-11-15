package com.vetcare.petmeds.applicationPrimary.initialCheckUp.controller;

import com.vetcare.petmeds.applicationPrimary.initialCheckUp.dto.InitialCheckUpDTO;
import com.vetcare.petmeds.applicationPrimary.initialCheckUp.dto.InitialCheckUpResponseDTO;
import com.vetcare.petmeds.shared.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

public interface InitialCheckUpControllerInterface {

    @PostMapping
    @Operation(summary = "Cadastro de Check-up", description = "Cadastra um novo check-up inicial")
    ResponseEntity<InitialCheckUpResponseDTO> create(@RequestBody InitialCheckUpDTO checkUpDTO);

    @PutMapping("/edit/{id}")
    @Operation(summary = "Atualizar check-up", description = "Atualiza os dados de um check-up existente")
    ResponseEntity<InitialCheckUpResponseDTO> update(@PathVariable Long id, @RequestBody InitialCheckUpDTO checkUpDTO);

    @GetMapping("/all")
    @Operation(summary = "Listar todos os check-ups", description = "Retorna uma lista paginada de check-ups")
    ResponseEntity<Page<InitialCheckUpResponseDTO>> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar check-up por ID", description = "Busca os detalhes de um check-up pelo seu ID")
    ResponseEntity<InitialCheckUpResponseDTO> findById(@PathVariable Long id);

    @GetMapping("/animal/{animalId}")
    @Operation(summary = "Buscar check-ups por animal", description = "Busca check-ups associados a um animal")
    ResponseEntity<Page<InitialCheckUpResponseDTO>> findByAnimalId(@PathVariable Long animalId, @PageableDefault(page = 0, size = 10) Pageable pageable);

    @GetMapping("/vet/{veterinarianId}")
    @Operation(summary = "Buscar check-ups por veterinário", description = "Busca check-ups realizados por um veterinário")
    ResponseEntity<Page<InitialCheckUpResponseDTO>> findByVeterinarianId(@PathVariable Long veterinarianId, @PageableDefault(page = 0, size = 10) Pageable pageable);

    @GetMapping("/date-range")
    @Operation(summary = "Buscar check-ups por intervalo de datas", description = "Busca check-ups em um período específico")
    ResponseEntity<Page<InitialCheckUpResponseDTO>> findByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @PageableDefault(page = 0, size = 10) Pageable pageable);

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover check-up", description = "Remove um check-up do sistema")
    ResponseEntity<ResponseDTO> delete(@PathVariable Long id);
}
