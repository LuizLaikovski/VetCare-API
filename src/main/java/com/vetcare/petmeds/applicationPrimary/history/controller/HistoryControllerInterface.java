package com.vetcare.petmeds.applicationPrimary.history.controller;

import com.vetcare.petmeds.applicationPrimary.history.dto.HistoryRequestDTO;
import com.vetcare.petmeds.applicationPrimary.history.dto.HistoryResponseDTO;
import com.vetcare.petmeds.shared.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface HistoryControllerInterface {

    @PostMapping
    @Operation(summary = "Cadastro de Histórico", description = "Cadastra um novo registro de histórico")
    ResponseEntity<HistoryResponseDTO> create(@RequestBody HistoryRequestDTO requestDTO);

    @PutMapping("/edit/{id}")
    @Operation(summary = "Atualizar histórico", description = "Atualiza os dados de um histórico existente")
    ResponseEntity<HistoryResponseDTO> update(@PathVariable Long id, @RequestBody HistoryRequestDTO requestDTO);

    @GetMapping("/all")
    @Operation(summary = "Listar todos os históricos", description = "Retorna uma lista paginada de históricos")
    ResponseEntity<Page<HistoryResponseDTO>> findAll(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    );

    @GetMapping("/{id}")
    @Operation(summary = "Buscar histórico por ID", description = "Busca os detalhes de um histórico pelo seu ID")
    ResponseEntity<HistoryResponseDTO> findById(@PathVariable Long id);

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover histórico", description = "Remove um histórico do sistema")
    ResponseEntity<ResponseDTO> delete(@PathVariable Long id);
}
