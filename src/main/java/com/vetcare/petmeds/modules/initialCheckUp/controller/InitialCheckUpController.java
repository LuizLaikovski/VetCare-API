package com.vetcare.petmeds.modules.initialCheckUp.controller;

import com.vetcare.petmeds.modules.initialCheckUp.dto.InitialCheckUpDTO;
import com.vetcare.petmeds.modules.initialCheckUp.dto.InitialCheckUpResponseDTO;
import com.vetcare.petmeds.modules.initialCheckUp.service.InitialCheckUpService;
import com.vetcare.petmeds.shared.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
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
import java.util.Date;

@RestController
@RequestMapping("/initialCheckUp")
@AllArgsConstructor
@Tag(name = "Check-up Inicial", description = "Gerenciamento de rotas para os check-ups iniciais do animal")
public class InitialCheckUpController {
    private InitialCheckUpService service;

    @PostMapping("/create")
    @Operation(summary = "Cadastro de Check-up", description = "Cadastra um novo check-up inicial")
    public ResponseEntity<InitialCheckUpResponseDTO> create(@RequestBody InitialCheckUpDTO checkUpDTO) {
        return new ResponseEntity<>(service.create(checkUpDTO), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Atualizar check-up", description = "Atualiza os dados de um check-up existente")
    public ResponseEntity<InitialCheckUpResponseDTO> update(@PathVariable Long id, @RequestBody InitialCheckUpDTO checkUpDTO) {
        return ResponseEntity.ok(service.update(id, checkUpDTO));
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos os check-ups", description = "Retorna uma lista paginada de check-ups")
    public ResponseEntity<Page<InitialCheckUpResponseDTO>> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar check-up por ID", description = "Busca os detalhes de um check-up pelo seu ID")
    public ResponseEntity<InitialCheckUpResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/animal/{animalId}")
    @Operation(summary = "Buscar check-ups por animal", description = "Busca check-ups associados a um animal")
    public ResponseEntity<Page<InitialCheckUpResponseDTO>> findByAnimalId(@PathVariable Long animalId, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.findByAnimalId(animalId, pageable));
    }

    @GetMapping("/vet/{veterinarianId}")
    @Operation(summary = "Buscar check-ups por veterinário", description = "Busca check-ups realizados por um veterinário")
    public ResponseEntity<Page<InitialCheckUpResponseDTO>> findByVeterinarianId(@PathVariable Long veterinarianId, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.findByVeterinarianId(veterinarianId, pageable));
    }

    @GetMapping("/date-range")
    @Operation(summary = "Buscar check-ups por intervalo de datas", description = "Busca check-ups em um período específico")
    public ResponseEntity<Page<InitialCheckUpResponseDTO>> findByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.findByDateRange(start.atStartOfDay(), end.atTime(LocalTime.MAX), pageable));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover check-up", description = "Remove um check-up do sistema")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        ResponseDTO response = new ResponseDTO(
                new Date(),
                "Checkup deletado com sucesso",
                "Checkup do ID " + id + " foi deletado com sucesso"
        );
        return ResponseEntity.ok(response);
    }
}
