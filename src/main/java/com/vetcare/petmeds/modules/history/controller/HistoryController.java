package com.vetcare.petmeds.modules.history.controller;

import com.vetcare.petmeds.modules.history.dto.HistoryRequestDTO;
import com.vetcare.petmeds.modules.history.dto.HistoryResponseDTO;
import com.vetcare.petmeds.modules.history.service.HistoryService;
import com.vetcare.petmeds.shared.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/history")
@AllArgsConstructor
@Tag(name = "Histórico", description = "Gerenciamento de rotas para o histórico do animal")
public class HistoryController {

    private HistoryService historyService;

    @PostMapping("/create")
    @Operation(summary = "Cadastro de Histórico", description = "Cadastra um novo registro de histórico")
    public ResponseEntity<HistoryResponseDTO> create(@RequestBody HistoryRequestDTO requestDTO) {
        return new ResponseEntity<>(historyService.create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Atualizar histórico", description = "Atualiza os dados de um histórico existente")
    public ResponseEntity<HistoryResponseDTO> update(@PathVariable Long id, @RequestBody HistoryRequestDTO requestDTO) {
        return ResponseEntity.ok(historyService.update(id, requestDTO));
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos os históricos", description = "Retorna uma lista paginada de históricos")
    public ResponseEntity<Page<HistoryResponseDTO>> findAll(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(historyService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar histórico por ID", description = "Busca os detalhes de um histórico pelo seu ID")
    public ResponseEntity<HistoryResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(historyService.findById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover histórico", description = "Remove um histórico do sistema")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        historyService.delete(id);
        ResponseDTO response = new ResponseDTO(
                new Date(),
                "Histórico deletado com sucesso",
                "Histórico do ID " + id + " foi deletado com sucesso"
        );
        return ResponseEntity.ok(response);
    }
}
