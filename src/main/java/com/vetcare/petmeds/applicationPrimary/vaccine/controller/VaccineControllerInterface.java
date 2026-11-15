package com.vetcare.petmeds.applicationPrimary.vaccine.controller;

import com.vetcare.petmeds.applicationPrimary.vaccine.entity.VaccineEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface VaccineControllerInterface {
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar vacina por ID",
            description = "Busca as informações da vacina, através do ID"
    )
    ResponseEntity<VaccineEntity> getVaccineById(@PathVariable Long id);

    @GetMapping("/name/{name}")
    @Operation(
            summary = "Buscar vacinas por nome",
            description = "Busca vacinas através do seu nome, com a utilização de paginação"
    )
    ResponseEntity<Page<VaccineEntity>> getVaccineByName(
            @PathVariable String name,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @GetMapping("/all")
    @Operation(
            summary = "Listar todas as vacinas",
            description = "Retorna uma lista paginada de vacinas"
    )
    ResponseEntity<Page<VaccineEntity>> getAllVaccines(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @PostMapping
    @Operation(
            summary = "Cadastro de Vacina",
            description = "Cadastra uma nova vacina"
    )
    ResponseEntity<VaccineEntity> createVaccine(@RequestBody VaccineEntity vaccine);

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar vacina",
            description = "Atualiza os dados de uma vacina existente, buscando a vacina através do ID"
    )
    ResponseEntity<VaccineEntity> updateVaccine(@PathVariable Long id, @RequestBody VaccineEntity vaccine);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remover vacina",
            description = "Remove uma vacina do sistema através do ID"
    )
    ResponseEntity<Void> deleteVaccine(@PathVariable Long id);
}
