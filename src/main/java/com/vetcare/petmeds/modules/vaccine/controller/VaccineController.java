package com.vetcare.petmeds.modules.vaccine.controller;

import com.vetcare.petmeds.modules.vaccine.entity.VaccineEntity;
import com.vetcare.petmeds.modules.vaccine.service.VaccineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vaccine")
@AllArgsConstructor
@Tag(name = "Vacina", description = "Gerenciamento de rotas para as vacinas")
public class VaccineController {

    private VaccineService vaccineService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar vacina por ID", description = "Busca os detalhes de uma vacina pelo seu ID")
    public ResponseEntity<VaccineEntity> getVaccineById(@PathVariable Long id) {
        return ResponseEntity.ok(vaccineService.getById(id));
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Buscar vacinas por nome", description = "Busca vacinas pelo nome com paginação")
    public ResponseEntity<Page<VaccineEntity>> getVaccineByName(
            @PathVariable String name,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(vaccineService.getByName(name, pageable));
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todas as vacinas", description = "Retorna uma lista paginada de vacinas")
    public ResponseEntity<Page<VaccineEntity>> getAllVaccines(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(vaccineService.getAll(pageable));
    }

    @PostMapping("/create")
    @Operation(summary = "Cadastro de Vacina", description = "Cadastra uma nova vacina")
    public ResponseEntity<VaccineEntity> createVaccine(@RequestBody VaccineEntity vaccine) {
        return new ResponseEntity<>(vaccineService.createVaccine(vaccine), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar vacina", description = "Atualiza os dados de uma vacina existente")
    public ResponseEntity<VaccineEntity> updateVaccine(@PathVariable Long id, @RequestBody VaccineEntity vaccine) {
        vaccine.setId(id);
        return ResponseEntity.ok(vaccineService.updateVaccine(vaccine));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover vacina", description = "Remove uma vacina do sistema")
    public ResponseEntity<Void> deleteVaccine(@PathVariable Long id) {
        vaccineService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
