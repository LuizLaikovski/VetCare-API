package com.vetcare.petmeds.applicationPrimary.medicine.controller;

import com.vetcare.petmeds.applicationPrimary.medicine.dto.MedicineResponseDTO;
import com.vetcare.petmeds.applicationPrimary.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.shared.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface MedicineControllerInterface {

    @GetMapping("/{id}")
    @Operation(summary = "Buscar medicamento por ID", description = "Busca os detalhes de um medicamento pelo seu ID")
    ResponseEntity<MedicineResponseDTO> getMedicineById(@PathVariable Long id);

    @GetMapping("/name/{name}")
    @Operation(summary = "Buscar medicamentos por nome", description = "Busca medicamentos pelo nome com paginação")
    ResponseEntity<Page<MedicineResponseDTO>> getMedicineByName(
            @PathVariable String name,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @GetMapping("/all")
    @Operation(summary = "Listar todos os medicamentos", description = "Retorna uma lista paginada de medicamentos")
    ResponseEntity<Page<MedicineResponseDTO>> getAllMedicine(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @PostMapping
    @Operation(summary = "Cadastro de Medicamento", description = "Cadastra um novo medicamento")
    ResponseEntity<MedicineResponseDTO> createMedicine(@RequestBody MedicineEntity medicine);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medicamento", description = "Atualiza os dados de um medicamento existente")
    ResponseEntity<MedicineResponseDTO> updateMedicine(@PathVariable Long id, @RequestBody MedicineEntity medicine);

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover medicamento", description = "Remove um medicamento do sistema")
    ResponseEntity<ResponseDTO> deleteMedicine(@PathVariable Long id);
}
