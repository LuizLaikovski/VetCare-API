package com.vetcare.petmeds.applicationPrimary.medicine.controller;

import com.vetcare.petmeds.applicationPrimary.medicine.dto.MedicineResponseDTO;
import com.vetcare.petmeds.applicationPrimary.medicine.entity.MedicineEntity;
import com.vetcare.petmeds.applicationPrimary.medicine.service.MedicineService;
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

@RestController
@RequestMapping("/medicine")
@AllArgsConstructor
@Tag(name = "Medicamentos", description = "Gerenciamento de rotas para os medicamentos")
public class MedicineController {

    private MedicineService medicineService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar medicamento por ID", description = "Busca os detalhes de um medicamento pelo seu ID")
    public ResponseEntity<MedicineResponseDTO> getMedicineById(@PathVariable Long id) {
        return ResponseEntity.ok(medicineService.getMedicineById(id));
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Buscar medicamentos por nome", description = "Busca medicamentos pelo nome com paginação")
    public ResponseEntity<Page<MedicineResponseDTO>> getMedicineByName(
            @PathVariable String name,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(medicineService.getByName(name, pageable));
    }

    @GetMapping("/all")
    @Operation(summary = "Listar todos os medicamentos", description = "Retorna uma lista paginada de medicamentos")
    public ResponseEntity<Page<MedicineResponseDTO>> getAllMedicine(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(medicineService.getAllMedicines(pageable));
    }

    @PostMapping("/create")
    @Operation(summary = "Cadastro de Medicamento", description = "Cadastra um novo medicamento")
    public ResponseEntity<MedicineResponseDTO> createMedicine(@RequestBody MedicineEntity medicine) {
        return new ResponseEntity<>(medicineService.createMedicine(medicine), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar medicamento", description = "Atualiza os dados de um medicamento existente")
    public ResponseEntity<MedicineResponseDTO> updateMedicine(@PathVariable Long id, @RequestBody MedicineEntity medicine) {
        return ResponseEntity.ok(medicineService.updateMedicine(id, medicine));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover medicamento", description = "Remove um medicamento do sistema")
    public ResponseEntity<ResponseDTO> deleteMedicine(@PathVariable Long id) {
        return new ResponseEntity<>(medicineService.deleteMedicine(id), HttpStatus.NO_CONTENT);
    }
}
